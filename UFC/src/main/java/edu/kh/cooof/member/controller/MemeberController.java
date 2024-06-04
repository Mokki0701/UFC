package edu.kh.cooof.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // 로그인 
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("login")
	public String memberLogin(
			RedirectAttributes ra,
			Member inputMember,
			Model model) {
		return null;
	}
	
	
	
	// 회원가입
	@GetMapping("signup")
	public String signup() {
		return "member/signup";
	}

	
	@PostMapping("signup")
	public String memberSignup(
			  @RequestParam("memberEmailId") String memberEmailId,
			  @RequestParam("memberEmailDomain") String memberEmailDomain,
			  Member inputMember,
			  @RequestParam("memberAddress") String[] memberAddress,
			  RedirectAttributes ra) {
			        
		// 이메일 아이디와 도메인을 합쳐서 이메일 주소를 만듭니다.
		String email = memberEmailId + "@" + memberEmailDomain;
		// 만든 이메일 주소를 DTO 객체에 설정합니다.
	    inputMember.setMemberEmail(email);
	
		int result = service.memberSignup(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = inputMember.getMemberLastName() + inputMember.getMemberFirstName() 
			+ "님 가입을 환영합니다";
			path = "/";
		} else {
			message = "회원 가입 실패";
			path = "member/signup";
		}
		
		ra.addFlashAttribute("message", message);
		
		
		return "redirect:" + path;
		
	}
}
