	package edu.kh.cooof.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.main.model.sevice.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("main")
public class MainController {
	
	

	private final MainService service; 
	
	// 길을 잃은 비로그인 사용자를 옳은 길로 인도하기
	// 홈으로 보내
	@GetMapping("goHome")
	public String goHome(
			@RequestParam(value="message", required = false) String message,
			Model model
			) {
		if(message != null) {
			model.addAttribute("message", message);			
		}
		
		return"index";
	}

	// 도서관 메인 페이지로 이동
	@GetMapping("libMain")
	public String libMain() {
		return "liblary/libMain";
	}
	
	
	
	
	
	

	
}
