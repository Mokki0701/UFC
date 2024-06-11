package edu.kh.cooof.lesson.calendar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lesson.calendar.model.service.LessonCalendarService;
import edu.kh.cooof.lesson.list.controller.LessonListController;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/calendar")
@RequiredArgsConstructor
public class LessonCalendarController {
	
	private final LessonCalendarService service;
	private final LessonListService listService;
	
	// 요청 주소 lesson/calender
	@GetMapping("")
	@ResponseBody
	public List<Lesson> calendarSelect(
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		// 로그인한 회원 번호로 수업 조회
		List<Lesson> lessonsCalendar = service.selectCalendar(loginMember.getMemberNo());
		
		return lessonsCalendar;
	}
	

}
