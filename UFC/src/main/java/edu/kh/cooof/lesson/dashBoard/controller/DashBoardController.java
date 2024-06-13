package edu.kh.cooof.lesson.dashBoard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lesson.common.pdf.ThymeleafParser;
import edu.kh.cooof.lesson.dashBoard.dto.AttendanceDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonInstructorDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lesson.dashBoard.service.DashBoardService;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/lesson")
public class DashBoardController {
	
	private final DashBoardService service;
	
	
	//화면 출력
	@GetMapping("dashboard")
	private String dashboard(
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		//회원이 듣는 강의 리스트 조회
		int loginMemberId = loginMember.getMemberNo();
		List<LessonListDTO> lessonList = service.findLesson(loginMemberId);
		List<LessonInstructorDTO> instructorLessons = service.instructorLesson(loginMemberId);
		List<LessonListDTO> lessonBookMarks = service.bookmarkList(loginMemberId);
		List<Map<String, Object>> certificateList = service.getPerfectAttendanceLessons(loginMember.getMemberNo());
		
		//즐겨찾기 강의 목록
		
		
		model.addAttribute("lessonList",lessonList);
		model.addAttribute("instructorLessons",instructorLessons);
		model.addAttribute("lessonBookMarks",lessonBookMarks);
		model.addAttribute("certificateList",certificateList);
		
		return "lessonDashBoard/dashBoard";
	}
	
	@PostMapping("dashboard")
	@ResponseBody
	private int dashboard(
			@RequestBody LessonListDTO lessonListDTO,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		//별점, 강의번호 넣기
		lessonListDTO.setMemberNo(loginMember.getMemberNo());
		
		int result = service.insertReview(lessonListDTO);
		
		return result;
	}
	
	
	//내가 등록한 강의의 별점 찾기

		@GetMapping("dashboard/star")
		@ResponseBody
		private int reviewRating(

				@RequestParam("lessonNo") int lessonNo,
				@SessionAttribute("loginMember")Member loginMember

				) {
		    LessonListDTO lessonList = new LessonListDTO();
		    lessonList.setMemberNo(loginMember.getMemberNo());
		    lessonList.setLessonNo(lessonNo);
			int starRating = service.findStar(lessonList);
			
			return starRating;

		}
		
		
		//학생 출석부 조회
		@GetMapping("dashboard/attendance")
		@ResponseBody
		public List<LessonListDTO> attendance(
				@RequestParam("lessonNo") int lessonNo,
				@RequestParam("date") String date
				) {
			
			LessonListDTO studentList = new LessonListDTO();
			
			studentList.setDate(date.replaceAll("-",""));
			studentList.setLessonNo(lessonNo);
			
			// 출석 리스트 조회
			List<LessonListDTO> attendanceList = service.confirmLesson(studentList);	
			
			return attendanceList;
		}
		
		
		
		//출석 등록 
		@PostMapping("dashboard/submit")
		@ResponseBody
		public int saveAttendance(
				@RequestBody List<AttendanceDTO> attendanceList
				, Model model
				){
			
			
			int del = service.deleteList(attendanceList);
			int result = service.addList(attendanceList);
//			model.addAttribute("msg", "성공함");
			
			return result;
		}
		
		
		//강사 별점 리뷰 확인
		@GetMapping("dashboard/review")
		@ResponseBody
		private int reviewStar(
				@RequestParam("lessonNo") int lessonNo
				) {
			
			int grade = service.checkReview(lessonNo);
			
			
			return grade;
		}
		
	
		
		// 출석현황
		@PostMapping("dashboard/attendanceStatus")
		@ResponseBody
		private List<AttendanceDTO> attendanceStatus(
				@RequestBody AttendanceDTO attendanceStatus
				){
			
			List<AttendanceDTO> attendanceCheck = service.statusCheck(attendanceStatus); 
			
			return attendanceCheck;
		}
		
		//출석률 테스트 -> 나중에 수정하기 
		@GetMapping("api/attendance")
		@ResponseBody
		private List<Map<String, Object>> getAttendanceRate(
				@RequestParam("memberNo") int memberNo
				){
			
			return service.getAttendanceRates(memberNo);
		}
		
		//즐겨찾기 삭제하기
		@GetMapping("bookmarkRemove")
		@ResponseBody
		private int removeBookmark(
				@RequestParam("lessonNo") int lessonNo,
				@SessionAttribute("loginMember") Member loginMember,
				Model model
				) {
			
			LessonListDTO lessonList = new LessonListDTO();
			lessonList.setLessonNo(lessonNo);
			lessonList.setMemberNo(loginMember.getMemberNo());

			int removeBookmark = service.bookmarkRemove(lessonList);
			String message = null;
			
			if(removeBookmark > 0) {
				message = "즐겨찾기 과목이 삭제 되었습니다";
			}
			
			model.addAttribute("message",message);
			
			return removeBookmark;
		}
		

		@PostMapping(value = "dashboard/certificateReq", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody // 메서드의 반환값이 HTTP 응답 본문으로 직접 작성됨을 나타냅니다.
		private ResponseEntity<Resource> lessonList(
		        @SessionAttribute("loginMember") Member loginMember, // 세션에서 loginMember 객체를 가져옵니다.
		        @RequestParam("certificateTitle") int lessonNo // 요청 파라미터에서 certificateTitle을 가져옵니다.
		) throws IOException {

		    // 회원 정보와 강의 정보를 가져옵니다.
		    Member certificateMember = service.selectCertificateMember(loginMember.getMemberNo());
		    Lesson certificateLesson = service.selectCertificateLesson(lessonNo);

		    // 회원과 강의 정보를 맵에 저장합니다.
		    Map<String, Object> map = new HashMap<>();
		    map.put("certificateLesson", certificateLesson);
		    map.put("certificateMember", certificateMember);

		    // HTML 템플릿을 문자열로 파싱합니다.
		    String html = ThymeleafParser.parseHtmlFileToString("certificateTemplate", map);

		    // 파일 이름을 Base64로 인코딩하여 안전하게 변환합니다.
//		    String fileName = Base64.encodeBase64URLSafeString((loginMember.getMemberNo() + "_" + certificateLesson.getLessonTitle() + "_수료증").getBytes("UTF-8"));

		    String fileName = new String((loginMember.getMemberNo() + "_" + certificateLesson.getLessonTitle() + "_수료증").getBytes("UTF-8"));
		    // HTML을 PDF로 변환하여 파일에 저장합니다.
		    String savedFilePath = ThymeleafParser.generateFromHtml(
		            "C:\\mokkie\\lesson", // 파일이 저장될 경로입니다.
		            fileName, // 인코딩된 파일 이름을 사용합니다.
		            html 
		    );
		    
		    
		    
		    // PDF 파일을 FileChannel로 응답합니다.
		    

		    System.out.println("PDF saved at: " + savedFilePath); // 파일 저장 경로를 출력합니다.

		    // PDF 파일을 클라이언트로 전송하기 위해 File 객체로 로드합니다.
		    File pdfFile = new File(savedFilePath);
		    InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile)); // 파일을 InputStreamResource로 변환합니다.

		    String encodingName = URLEncoder.encode( pdfFile.getName(), "UTF-8");
		    
		    // ResponseEntity를 사용하여 파일을 응답으로 보냅니다.
		    return ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodingName + "\"") // 파일이 다운로드되도록 Content-Disposition 헤더를 설정합니다.
		            .contentType(MediaType.APPLICATION_PDF) // 콘텐츠 타입을 PDF로 설정합니다.
		            .contentLength(pdfFile.length()) // 파일의 길이를 설정합니다.
		            .body(resource); // 파일 내용을 응답 본문으로 설정합니다.
		}
		
		
		
	
}
