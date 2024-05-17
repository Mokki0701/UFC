package edu.kh.cooof.lesson.dashBoard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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
		
		model.addAttribute("lessonList",lessonList);
		
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
			@RequestBody LessonListDTO lessonNo,
			@SessionAttribute("loginMember")Member loginMember
			) {
		
		lessonNo.setMemberNo(loginMember.getMemberNo());
		
		
		int starRating = service.findStar(lessonNo);
		
		return starRating;
		
	}
	
	

	
	
}
