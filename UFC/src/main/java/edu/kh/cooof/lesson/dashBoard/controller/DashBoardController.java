package edu.kh.cooof.lesson.dashBoard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lesson.dashBoard.dto.AttendanceDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonInstructorDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lesson.dashBoard.service.DashBoardService;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		
		model.addAttribute("lessonList",lessonList);
		model.addAttribute("instructorLessons",instructorLessons);
		
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
				){
			
			int result = service.addList(attendanceList);
			
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
		
	
}
