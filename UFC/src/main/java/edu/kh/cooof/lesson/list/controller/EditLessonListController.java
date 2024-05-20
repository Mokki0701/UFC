package edu.kh.cooof.lesson.list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.lesson.list.model.service.EditLessonListService;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/listEdit")
@RequiredArgsConstructor
public class EditLessonListController {
	
	private final EditLessonListService service;
	
	@GetMapping("insert")
	public String listInsert() {
		
		
		return "lesson/lessonList/lessonWrite";
	}
	

}
