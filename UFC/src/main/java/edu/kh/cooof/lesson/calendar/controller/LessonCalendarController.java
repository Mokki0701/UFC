package edu.kh.cooof.lesson.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.cooof.lesson.calendar.model.service.LessonCalendarService;
import edu.kh.cooof.lesson.list.controller.LessonListController;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/calender")
@RequiredArgsConstructor
public class LessonCalendarController {
	
	private final LessonCalendarService service;
	
	// 요청 주소 lesson/calender
	@GetMapping("")
	@ResponseBody
	public String calendarSelect() {
		
		
		return new String();
	}
	

}
