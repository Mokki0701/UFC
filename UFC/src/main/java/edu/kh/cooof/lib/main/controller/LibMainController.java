package edu.kh.cooof.lib.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.lib.main.model.service.LibMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("libMain")
@Slf4j
public class LibMainController {

	private final LibMainService service;
	
	
	
	// 좌석 관리 페이지로 이동
	@GetMapping("managingSeatPage")
	private String managingSeatPage () {
	
		return "lib/seat/managingSeatPage";
	}
	
	
}
