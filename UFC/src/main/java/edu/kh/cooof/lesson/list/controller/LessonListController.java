package edu.kh.cooof.lesson.list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.lesson.main.controller.LessonController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/list")
@RequiredArgsConstructor
public class LessonListController {
	
	@GetMapping("") // get 요청은 바로 레슨 메인으로
	public String lessonListMainPage() {
		
		return "lesson/lessonList/lessonList";
	}

}
