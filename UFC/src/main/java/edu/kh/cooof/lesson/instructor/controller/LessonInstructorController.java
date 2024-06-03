package edu.kh.cooof.lesson.instructor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lesson.instructor.model.dto.LessonInstructor;
import edu.kh.cooof.lesson.instructor.model.service.LessonInstructorService;
import edu.kh.cooof.lesson.list.controller.LessonListController;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
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
			
	) {

		return "lesson/lessonMain";
	}
}
