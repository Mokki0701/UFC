package edu.kh.cooof.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/")
public class RootController {

	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
		return "redirect:/";
	}
	
	
	
	
}
