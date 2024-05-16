package edu.kh.cooof.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({ "loginMember" })
@RequestMapping("member")
public class MemeberController {

	private final MemberService service;

	// 빠른 로그인
	@GetMapping("quickLogin")
	public String quickLogin(@RequestParam("memberEmail") String memberEmail, Model model,
			RedirectAttributes ra) {

		Member loginMember = service.quickLogin(memberEmail);

		if (loginMember == null) {
			ra.addFlashAttribute("message", "해당 회원이 존재하지 않습니다");
		}
		
		model.addAttribute("loginMember", loginMember);

		System.out.printf("loginMember : %s", loginMember.getMemberEmail());
		
		return "redirect:/";

	}
	
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		return "redirect:/";
		
	}
	

}
