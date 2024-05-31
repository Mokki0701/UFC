package edu.kh.cooof.lib.seat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.seat.model.mapper.LibSeatMapper;
import edu.kh.cooof.lib.seat.model.service.LibSeatService;
import edu.kh.cooof.lib.space.model.mapper.SpaceMapper;
import edu.kh.cooof.lib.space.model.service.SpaceService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("lib/seats")
public class LibSeatController {

	private final LibSeatService service;
	private final LibSeatMapper mapper;
	private final SpaceService spaceService;
	private final SpaceMapper spaceMapper;

	// 관리자 : 저장된 좌석 현황 불러오기
	@GetMapping("/data")
	public ResponseEntity<List<LibSeatDTO>> getSeatsData() {
		try {
			List<LibSeatDTO> seats = service.getAllSeats();
			log.debug("Seats: {}", seats);
			return ResponseEntity.ok(seats);
		} catch (Exception e) {
			log.error("Error fetching seats data", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 관리자 : 좌석 변경 현황 저장하기
	@PostMapping("/save")
	public ResponseEntity<Map<String, String>> saveSeats(@RequestBody List<LibSeatDTO> seats) {
		log.debug("Received seats: {}", seats);
		Map<String, String> response = new HashMap<>();
		try {
			service.saveSeats(seats);
			response.put("message", "좌석 편집 저장 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error saving seats", e);
			response.put("message", "좌석 편집 저장 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 열람실 좌석 이용하기
	@PostMapping("/useSeat")
	public ResponseEntity<Map<String, String>> useSeat(@RequestBody LibSeatDTO libSeat,
			@SessionAttribute("loginMember") Member loginMember, HttpServletRequest request, HttpSession session) {

		String result = null;
		String message = null;
		int memberNo = loginMember.getMemberNo();
		int isMemberUsing = service.isMemberUsing(memberNo);
		int seatCondition = libSeat.getCondition();

		// 좌석 컨디션이 1이 아닌 경우(이용 가능한 좌석이 아닌 경우)
		if (seatCondition != 1) {
			message = "비어있는 좌석만 이용 가능합니다";
			result = "fail";
		} else if (isMemberUsing == 1) {
			// 현재 회원이 이용 중인 좌석이 있을 경우
			message = "현재 회원님은 이용중인 자리가 있습니다.";
			result = "fail";
		} else {
			// 이용 가능한 좌석이며, 현재 회원이 이용 중인 좌석이 없을 경우
			// -1 : 이용 등록 성공
			int useSeatResult = service.useSeat(libSeat.getSeatNo(), memberNo);

			// 이용 등록 성공한 경우
			if (useSeatResult == -1) {
				message = "좌석 이용 등록 성공!";
				result = "success";

				// 회원이 이용 중인 자리 session에 저장하기
				Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session
						.getAttribute("memberAndSeatSession");
				if (memberAndSeatSession == null) {
					memberAndSeatSession = new HashMap<>();
				}
				memberAndSeatSession.put(memberNo, libSeat.getSeatNo());

				session.setAttribute("memberAndSeatSession", memberAndSeatSession);

				System.out.println("이용시작 회원번호 : 자리번호(DB): " + memberAndSeatSession);

			} else {
				message = "좌석 이용 실패...";
				result = "fail";
			}

		}

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("result", result);

		return ResponseEntity.ok(response);
	}

	// 현재 회원의 열람실 이용 정보 가져오기
	@GetMapping("/seatUsage")
	@ResponseBody
	public LibSeatDTO getSeatUsage(HttpSession session) {
		// 세션에서 로그인한 회원 정보 가져오기
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember != null) {
			int memberNo = loginMember.getMemberNo();
			// 회원 번호로 좌석 이용 정보 조회
			return service.getSeatUsageByMemberNo(memberNo);
		}
		return null;
	}

	// 열람실 이용 종료하기
	@PostMapping("/stopUsingSeat")
	public ResponseEntity<Map<String, String>> stopUsingSeat(@SessionAttribute("loginMember") Member loginMember,
			HttpSession session) {
		int memberNo = loginMember.getMemberNo();
		int seatNo = service.stopUsingSeat(memberNo);

		String message;
		String result;
		if (seatNo > 0) {
			// 세션에서 회원과 자리 정보를 담은 맵 가져오기
			Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session
					.getAttribute("memberAndSeatSession");
			if (memberAndSeatSession != null) {
				memberAndSeatSession.remove(memberNo);
			}

			System.out.println("이용종료 회원번호 : 자리번호(DB): " + memberAndSeatSession);

			message = "좌석 이용 종료 성공!";
			result = "success";
		} else {
			message = "이용 중인 좌석이 없습니다.";
			result = "fail";
		}

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("result", result);
		response.put("seatNo", String.valueOf(seatNo));

		return ResponseEntity.ok(response);
	}

	// 열람실 자리 연장하기
	@PostMapping("/extend")
	@ResponseBody
	public String extendSeat(HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember != null) {
			int memberNo = loginMember.getMemberNo();
			boolean result = service.extendSeat(memberNo);
			return result ? "success" : "fail";
		}
		return "fail";
	}

	@PostMapping("/checkAvailReservation")
	@ResponseBody
	public Map<String, Object> checkAvailReservation(@SessionAttribute("loginMember") Member loginMember, Model model,
			@RequestBody LibSeatDTO libSeat, RedirectAttributes ra) {
		int memberNo = loginMember.getMemberNo();
		int seatNo = libSeat.getSeatNo();
		String startTime = libSeat.getStartTime();
		String message = null;
		boolean success = false;

		Map<String, Object> result = new HashMap<>();
		int terminal = 0;

		// 1. 회원이 이용 중인 공간이 있는지 확인
		if (terminal == 0) {
			int memberSpaceUsingCheck = spaceMapper.memberSpaceUsingCheck(memberNo);
			if (memberSpaceUsingCheck == 1) {
				message = "회원님은 현재 이용 중인 공간이 있습니다.";
				result.put("memberSpaceUsingCheck", "회원님은 현재 이용 중인 공간이 있습니다.");
				terminal = 1;
			}
		}

		// 2. 회원이 이용 중인 열람실이 있는지 확인
		if (terminal == 0) {
			int isMemberUsing = service.isMemberUsing(memberNo);
			if (isMemberUsing == 1) {
				message = "회원님은 현재 이용 중인 열람실이 있습니다.";
				result.put("isMemberUsing", message);
				terminal = 1;
			}
		}

		// 3. 나에게 다른 예약이 있는지 확인(열람실, 공간 예약 포함)
		if (terminal == 0) {
			int ifYouHaveAnyOtherReservation = spaceService.ifYouHaveAnyOtherReservation(memberNo);
			if (ifYouHaveAnyOtherReservation == 1) {
				message = "회원님은 이미 다른 예약이 있으세요.";
				result.put("ifYouHaveAnyOtherReservation", message);
				terminal = 1;
			}
		}

		// 5. 현재 공간의 이용 가능 여부 확인
		if (terminal == 0) {
			int chckSeatConditon = mapper.chckSeatConditon(seatNo);
			if (chckSeatConditon >= 1) {
				message = "해당 공간은 이미 다른 사람이 이용 중입니다.";
				terminal = 1;
				result.put("chckSeatConditon", message);
			}
		}

		// 6. 모든 조건을 통과했을 때 예약 수행
		if (terminal == 0) {
			int checkStartTime = service.checkStartTime(seatNo, startTime);
			if (checkStartTime == 1) {
				message = "현재 이용중인 자리의 종료 예정시간 이전엔 예약이 불가합니다.";
				terminal = 1;
				result.put("checkStartTime", message);
			}
		}

		if (terminal == 0) {
			// 예약 성공
			success = true;
			message = "예약이 성공적으로 완료되었습니다.";
		}

		result.put("success", success);
		result.put("message", message);
		ra.addFlashAttribute("message", message);

		return result;
	}

}
