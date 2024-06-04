package edu.kh.cooof.lesson.instructor.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lesson.common.LessonUtil;
import edu.kh.cooof.lesson.common.pdf.PdfService;
import edu.kh.cooof.lesson.instructor.model.dto.LessonInstructor;
import edu.kh.cooof.lesson.instructor.model.service.LessonInstructorService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 강사 페이지 컨트롤러

@Slf4j
@Controller
@RequestMapping("lesson/inst")
@RequiredArgsConstructor
public class LessonInstructorController {

	private final LessonInstructorService service;
	private final PdfService pdfService;

	@GetMapping("")
	public String lessonInstructorPage(Model model) {

		Map<String, Object> map = new HashMap<>();

		map = service.selectInstList();

		model.addAttribute("instList", map.get("instList"));

		return "lesson/lessonInstructor/instructorMain"; // 강사 메인 페이지로 이동
	}

	@GetMapping("detail/{instNo:[0-9]+}")
	public String instDetail(@PathVariable("instNo") int instNo, // 클릭된 강사의 강사 번호
			Model model) {

		LessonInstructor inst = service.selectDetailInst(instNo);

		model.addAttribute("inst", inst);

		return "lesson/lessonInstructor/instructorDetail"; // 강사 상세보기로 이동
	}

	@GetMapping("reg")
	public String lessonInstructorRegPage(
			@SessionAttribute("loginMember") Member loginMember) {

		return "lesson/lessonInstructor/instructorReg";

	}

	@PostMapping("reg")
	public String lessonInstructorRegister(
	        @RequestParam("instIntro") String instIntro, // 자기소개
	        @RequestParam("instCategory") String instCategory, // 강의 분야
	        @RequestParam("pdfFile") MultipartFile pdfFile, // PDF 이력서
	        @SessionAttribute("loginMember") Member loginMember,
	        RedirectAttributes redirectAttributes) {

	    try {
	        // 이미 강사로 신청했는지 여부 확인
	        Integer reqCheck = service.checkRequest(loginMember.getMemberNo());

	        // 대기상태일 경우
	        if (reqCheck != null && reqCheck == 2) {
	            redirectAttributes.addFlashAttribute("message", "이미 강사 신청하셨습니다!");
	            return "redirect:/lesson/inst";
	        }

	        // 강사 테이블에 존재하지 않을 경우
	        if (reqCheck == null) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("instCategory", instCategory);
	            map.put("instIntro", instIntro);
	            map.put("memberNo", loginMember.getMemberNo());
	            map.put("loginMember", loginMember);

	            // 타임리프로 PDF 만들어본다 테스트!!!!!!!!!!!!!!!!!!!
	            // PDF 이력서 파일 저장
	            String resumePath = pdfService.saveUploadedPdf(pdfFile, loginMember.getMemberNo());

	            // PDF 파일 생성 및 저장
	            map.put("resumePath", resumePath);
	            String pdfFilePath = pdfService.generatePdf("pdfTemplate", map);
	            //////////////////////////////테스트끗

	            // PDF 생성이 완료되면 결과 메시지 설정
	            redirectAttributes.addFlashAttribute("message", "PDF 생성 성공: " + pdfFilePath);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("message", "PDF 생성 실패: " + e.getMessage());
	    }

	    return "redirect:/lesson/inst";
	}

	
	@GetMapping("regCheck")
	public String regCheck(
			Model model
			) {
		
		List<Member> members = service.instRegCheck();
		
		 model.addAttribute("members", members);
		
		return "lesson/lessonInstructor/instructorRegCheck";
	}
	
	// 강사 요청 승인 (update로 활동중 상태로 만들어준다)
	@PostMapping("accept")
	@ResponseBody
	public int instAccept(
			@RequestBody int memberNo
			) {
		
		int result = service.instAccept(memberNo);
		
		return result;
	}
	
	// 강사 요청 거절 (강사 테이블에서 행 삭제) 
	@PostMapping("reject")
	@ResponseBody
	public int instReject(
			@RequestBody int memberNo
			) {
		
		int result = service.instReject(memberNo);
		
		return result;
	}
	
	
}
