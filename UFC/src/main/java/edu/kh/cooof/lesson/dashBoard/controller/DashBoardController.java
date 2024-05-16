package edu.kh.cooof.lesson.dashBoard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lesson.dashBoard.service.DashBoardService;
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
	
	

	
	
}
