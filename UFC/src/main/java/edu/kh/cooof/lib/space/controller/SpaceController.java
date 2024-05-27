package edu.kh.cooof.lib.space.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.service.SpaceService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("lib/space")
public class SpaceController {

	private final SpaceService service;

	// 관리자 : 공간 편집 저장하기
	@PostMapping("/saveSpaceManagement")
	public ResponseEntity<Integer> saveSpaceManagement(@RequestBody List<SpaceDTO> spaceList) {
		int insertedCount = service.saveSpaceManagement(spaceList);
		return ResponseEntity.ok(insertedCount);
	}

	// 관리자, 일반회원 : 공간 정보 불러오기(위치, 번호)
	@GetMapping("/getSpaces")
	public ResponseEntity<List<SpaceDTO>> getSpaces() {
		List<SpaceDTO> spaces = service.getAllSpaces();
		spaces.forEach(space -> {
			System.out.println(space); // 로그에 출력하여 데이터 확인
		});
		return ResponseEntity.ok(spaces);
	}

	// 회원의 공간 이용 요청 보내기
	@PostMapping("/wannaUseSpace")
	public String postMethodName(
			HttpSession session, 
			HttpServletRequest request, 
			@RequestParam("spaceNo") int spaceNo,
			Model model) {

		
		
		String message = null;
		String path = request.getHeader("Referer"); // 이전 페이지 URL 가져오기

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null) {
			message = "로그인 후 이용해 주세요";
			path = "redirect:/"; // 로그인 페이지로 리다이렉트
		} else {
			int memberNo = loginMember.getMemberNo();
			int letUseSpace = service.useSpace(memberNo, spaceNo);
			System.out.printf("letUseSpace : %d%n", letUseSpace);
			message = "이용 요청이 처리되었습니다.";

			// 공간 이용 정보를 map에 담아 session으로 올리기
			Map<Integer, Integer> memberAndSpaceSession = (Map<Integer, Integer>) session
					.getAttribute("memberAndSpaceSession");
			
			if (memberAndSpaceSession == null) {
				memberAndSpaceSession = new HashMap<>();
			}
			memberAndSpaceSession.put(memberNo, spaceNo);
			
			session.setAttribute("memberAndSpaceSession",memberAndSpaceSession);
			
			System.out.println("이용 시작 회원 번호 : 자리번호(DB) == " + memberAndSpaceSession);

			path = "redirect:" + path; // 현재 페이지로 리다이렉트
		}

		
		model.addAttribute("message", message);

		return path;
	}

}
