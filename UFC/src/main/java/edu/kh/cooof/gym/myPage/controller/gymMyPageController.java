package edu.kh.cooof.gym.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.gym.myPage.model.service.gymMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/gym/myPage")
public class gymMyPageController {
	
	private final gymMyPageService service;
	
	@GetMapping("/gymMyPage")
	public String gymMyPage() {
		
		return "gym/myPage/gymMyPage";
	}
	

}
