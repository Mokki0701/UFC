package edu.kh.cooof.lesson.list.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.service.EditLessonListService;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/listEdit")
@RequiredArgsConstructor
public class EditLessonListController {
	
	private final EditLessonListService service;
	private final LessonListService lessonService;
	
	@GetMapping("insert")
	public String listInsert(
			) {
		
		
		return "lesson/lessonList/lessonWrite";
	}
	
	// 게시글 작성
	@PostMapping("insert")
	public String  listInsert(
			@SessionAttribute("loginMember") Member loginMember,
			@ModelAttribute Lesson inputLesson, // 커맨드 객체
			@RequestParam("inputImg") MultipartFile inputImg, // 입력받은 썸네일 이미지
			RedirectAttributes ra // 리다이렉트 시 메세지 전달 용도
			) throws IllegalStateException, IOException {
		
		inputLesson.setMemberNo(loginMember.getMemberNo()); // 로그인한 멤버를 강사명으로 삽입 
		
		// 작성된 내용 전부 INSERT하는 서비스 호출 + 결과로 레슨 번호 반환
		int lessonNo = service.lessonInsert(inputLesson, inputImg);
		
		String path = null;
		String message = null;
		
		if (lessonNo > 0) {
			
			// 모집중 태그 추가
			service.setCloseYnToY(lessonNo);
			
			message = "수업 게시되었습니다.";
			
			// !! 경로 수정해야 함 (상세보기 화면으로) !! 
			path = "redirect:/lesson/list/" + lessonNo;
			
			
		} else {
			
			message = "수업 게시에 실패했습니다.";
			
			// 글 작성 화면으로 다시 보내기
			path = "lesson/lessonList/lessonWrite";
			
		}
		
		ra.addFlashAttribute("message", message);
		return path;
	}
	
	// 수정화면 전환
	@GetMapping("{lessonNo:[0-9]+}/update")
	public String lessonUpdateView(
			@PathVariable("lessonNo") int lessonNo,
			Model model,
			RedirectAttributes ra
							) {

		// 기존에 작성한 수업 상세 조회 서비스 호출
		Lesson lesson = lessonService.selectDetail(lessonNo);
		
		// 조회 결과 모델로 올리기
		model.addAttribute("lesson", lesson);
		
		// 수정 화면으로 포워드
		return "lesson/lessonList/lessonUpdate";
	}
	
	// 수업 수정
	@PostMapping("{lessonNo:[0-9]+}/update")
	public String lessonUpdate(
			@PathVariable("lessonNo") int lessonNo,
			@ModelAttribute Lesson inputLesson, // 커맨드 객체
			@RequestParam("inputImg") MultipartFile inputImg, // 입력받은 썸네일 이미지
			Model model,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
			inputLesson.setLessonNo(lessonNo); // 수정할 레슨 번호 lessonNo에 삽입
		
			// 작성된 내용 전부 INSERT하는 서비스 호출 + 결과로 레슨 번호 반환
			int result = service.lessonUpdate(inputLesson, inputImg);
		
		
			
		return "redirect:/lesson/list/" + lessonNo; // 확인해야됨 주소!!!!!!!
	}
	
	// 수업 삭제
	@GetMapping("{lessonNo:[0-9]+}/delete")
	public String lessonDelete(
			@PathVariable("lessonNo") int lessonNo
			) {
		
		int result = service.lessonDelete(lessonNo);
		
		
		return "redirect:/lesson/list";
	}
	
	
	
	

}
