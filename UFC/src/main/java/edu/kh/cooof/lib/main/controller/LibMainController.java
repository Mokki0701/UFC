package edu.kh.cooof.lib.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lib.main.model.service.LibMainService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("libMain")
@Slf4j
public class LibMainController {

	private final LibMainService service;

	// ufc메인으로 이동
	@GetMapping("/toUfcMain")
	public String toUfcMain() {
		return "/index";
	}
	
	
	// 도서관 메인으로 이동
	@GetMapping("/toLibMain")
	public String toLibMain() {
		
		return "/liblary/libMain";
	}
	
	
	// 자리 관리 페이지로 이동
	@GetMapping("/managingSeatPage")
	public String managingSeatPage(HttpSession session, RedirectAttributes ra) {
		// 세션에서 loginMember 객체를 가져옴
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 사용자 권한이 3인지 확인
		if (loginMember != null && loginMember.getMemberAuthority() == 3) {
			return "lib/seat/managingSeatPage";
		} else {
			// 권한이 없으면 에러 페이지로 리디렉션
			ra.addFlashAttribute("message", "접근 권한이 없습니다.");
			return "common/error"; // 에러 페이지로 리디렉션
		}
	}

	// 열람실 조회 및 예약 페이지로 이동
	@GetMapping("/toUseSeat")
	public String bookingSeat(HttpSession session, RedirectAttributes ra) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;

		// 로그인이 된 경우에만 이동 가능하게 하기
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			path = "common/error";
		}

		if (loginMember != null && loginMember.getMemberAuthority() < 3) {
			path = "lib/seat/bookingSeat";
		}

		return path;
	}

	// 자리 연장 페이지로 이동하기
	@GetMapping("/toExtendSeat")
	public String extendSeat(HttpSession session, RedirectAttributes ra) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;

		// 로그인 안된 경우
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			path = "common/error";

		}

		// 로그인 된 경우
		if (loginMember != null && loginMember.getMemberAuthority() < 3) {
			path = "lib/seat/extendSeat";
		}

		return path;

	}
	// 공간 관리 페이지로 이동하기
	@GetMapping("managingSpace")
	public String managingSpace(HttpSession session, RedirectAttributes ra, Model model) {
		
		
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		
		String path = null;
		// loginMember.memberAuthority == 3 : 관리자 회원만 접근 가능하게 하기
		int memberAuthority = loginMember.getMemberAuthority();
		
		// 관리자가 아닌 경우
		if(memberAuthority < 3) {
			ra.addFlashAttribute("message", "해당 기능은 관리자만 이용 가능합니다.");
			
			path = "redirect:/";
		}
		
		// 관리자인 경우
		if(memberAuthority == 3) {
			path = "lib/space/libSpaceManaging";
		}
		
		model.addAttribute("memberAuthority", memberAuthority);
		return path;
		
	}
	

	// 공간 이용 페이지로 이동하기
	@GetMapping("toSpace")
	public String toSpace(HttpSession session, RedirectAttributes ra) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;

		// 필요한 세션 객체 생성하기
		// 1. 공간 세션 객체를 생성하기
		Map<Integer, Integer> memberAndSpaceSession = (Map<Integer, Integer>) session
				.getAttribute("memberAndSpaceSession");

		if (memberAndSpaceSession == null) {
			memberAndSpaceSession = new HashMap<>();
		}
		
		// 2. 열람실 세션 객체 생성하기
		Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session
				.getAttribute("memberAndSeatSession");
		
		if (memberAndSeatSession == null) {
			memberAndSeatSession = new HashMap<>();
		}
		
		
		// 로그인 안된 경우
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			path = "common/error";

		}

		// 로그인 된 경우
		if (loginMember != null && loginMember.getMemberAuthority() < 3) {
			path = "lib/space/libSpaceUsing";
		}

		return path;

	}

}
