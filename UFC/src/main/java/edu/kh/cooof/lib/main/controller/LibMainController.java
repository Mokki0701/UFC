package edu.kh.cooof.lib.main.controller;

import org.springframework.stereotype.Controller;
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
	public String extendSeat(
		HttpSession session,
		RedirectAttributes ra
			) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;
		
		// 로그인 안된 경우
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
			path = "common/error";
			
		}
		
		// 로그인 된 경우
		if (loginMember != null && loginMember.getMemberAuthority() < 3) {
			path = "lib/seat/extendSeat";
		}
		
		
		return path;
		
	}

}
