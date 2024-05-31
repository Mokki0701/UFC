package edu.kh.cooof.lesson.instructor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.lesson.instructor.model.service.LessonInstructorService;
import edu.kh.cooof.lesson.list.controller.LessonListController;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 강사 페이지 컨트롤러

@Slf4j
@Controller
@RequestMapping("lesson/inst")
@RequiredArgsConstructor
public class LessonInstructorController {
	
	private final LessonInstructorService service;
	
	@GetMapping("")
	public String lessonInstructorPage() {
		
		
		return "lesson/lessonInstructor/instructorMain";
	}
	
	
	
	
	
	
	
	
	
	

}
