package edu.kh.cooof.lesson.main.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson")
@RequiredArgsConstructor
public class LessonController {
	
	 private static final String FILE_DIRECTORY = "C:\\mokkie\\lesson\\instReg";
	

	@GetMapping("") // get 요청은 바로 레슨 메인으로
	public String lessonMainPage() {

		return "lesson/lessonMain";
	}

	// 테스트용 링크
	@GetMapping("test")
	public String lessonTest() {

		return "lesson/lessonTest";
	}
	
	@GetMapping("download/{fileName}")
	public ResponseEntity<Resource> lessonDownload(
			@PathVariable String fileName
			) {
		
		
		
	}

}
