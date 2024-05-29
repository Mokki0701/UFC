package edu.kh.cooof.lib.main.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lib.main.model.service.LibMemberService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("libMember")
public class LibMemberController {

	private final LibMemberService service;
	
	@GetMapping("member")
	public String member(
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		Map<String, Object> map = service.getMemberInfo(loginMember.getMemberNo());
		
		model.addAttribute("seat", map.get("seat"));
		model.addAttribute("space", map.get("space"));
		model.addAttribute("loanList", map.get("loanList"));
		model.addAttribute("rentList", map.get("rentList"));
		
		return "lib/member/member";
	}
	
}
