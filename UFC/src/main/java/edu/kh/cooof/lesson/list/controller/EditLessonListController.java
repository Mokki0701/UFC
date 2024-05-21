package edu.kh.cooof.lesson.list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("insert")
	public String listInsert(
			) {
		
		
		return "lesson/lessonList/lessonWrite";
	}
	
	@PostMapping("insert")
	public String  listInsert(
			@SessionAttribute("loginMember") Member loginMember,
			
			
			
			RedirectAttributes ra
			
			) {
		
		
		
		return "lesson/lessonMain";
	}
	

}
