package edu.kh.cooof.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.gym.trainerSelect.model.dto.PtPrice;
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
	public String login(
			) {
		
	
		return "member/login";
	}
	
	// 로그인 하기
	@PostMapping("loginMember")
	public String memberLogin(
			RedirectAttributes ra,
			Member inputMember,
			Model model) {
		
		
		Member loginMember = service.login(inputMember);
		
		
		
		
		if(loginMember == null) {
			ra.addFlashAttribute("message" , "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		if(loginMember != null ) {
			ra.addFlashAttribute("message" , "로그인 성공");
			model.addAttribute("loginMember" , loginMember);
			
		}
		
		return "redirect:/";
	}
	
	
	
	// 회원가입
	@GetMapping("signup")
	public String signup() {
		return "member/signup";
	}

	// 회원가입 
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
	
	// 아이디 중복 확인
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(
			 @RequestParam("memberEmail") String memberEmail		
			 ) {
		
		return service.checkEmail(memberEmail);
	}
	
	
	
// ------------------ 마이페이지 -------------------------------
	
	// 비밀번호 변경
	@PostMapping("changePw")
	public String changePw(
			@RequestParam("currentPw")String currentPw,
			@RequestParam("newPw")String newPw,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra
			) {
		
		int result = service.changePw(currentPw, newPw, loginMember);
		
		String message = null;
		String path = null;
		
		if(result == 0) {
			message = "현재 비밀번호가 일치하지 않습니다";
			
		} else {
			message = "비밀번호가 변경 되었습니다";
			
		}
		ra.addFlashAttribute("message", message);
		
		return "redirect:/";
	}
	
	
	@PostMapping("changeAnything")
	public String changeAnything(
			Member inputMember,
			@SessionAttribute("loginMember")Member loginMember,
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra) {
		
			int memberNo = loginMember.getMemberNo();
			inputMember.setMemberNo(memberNo);
			
			int result = service.changeAnything(inputMember, memberAddress);
			
			String message = null;
			
			
			
			if(result > 0) {
				message = "회원 정보 수정 성공~";
				loginMember.setMemberBirthday(inputMember.getMemberBirthday());
				
				loginMember.setMemberPhone(inputMember.getMemberPhone());
				
				loginMember.setMemberAddress(inputMember.getMemberAddress());
				
				
			} else {
				message = "회원 정보 수정 실패...";
			}
			
			ra.addFlashAttribute("message" ,message);
			
			return "redirect:/";
	}
	
	@PostMapping("memberDelete")
	public String memberDelete(
			@SessionAttribute("loginMember")Member loginMember
			) {
		service.memberDelete(loginMember.getMemberNo());
		
		
		return "redirect:/";
	}
	
	
}
