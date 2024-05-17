package edu.kh.cooof.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.main.model.sevice.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("main")
public class MainController {

	private final MainService service; 
	
	// 도서관 메인 페이지로 이동
	@GetMapping("libMain")
	public String libMain() {
		return "liblary/libMain";
	}
	
}
