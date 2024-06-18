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
	public ResponseEntity<Map<String, Object>> useSeat(@RequestBody LibSeatDTO libSeat,
			@SessionAttribute("loginMember") Member loginMember, HttpServletRequest request, HttpSession session) {

		String result = null;
		String message = null;
		int memberNo = loginMember.getMemberNo();
		int isMemberUsing = service.isMemberUsing(memberNo);
		int isMemberUsingSpace = service.isMemberUsingSpace(memberNo);
		int seatCondition = libSeat.getCondition();
		int userSeatNo = 0;
		

		// 좌석 컨디션이 1이 아닌 경우(이용 가능한 좌석이 아닌 경우)
		if (seatCondition != 1) {
			message = "비어있는 좌석만 이용 가능합니다";
			result = "fail";
		} else if (isMemberUsing == 1) {
			// 현재 회원이 이용 중인 좌석이 있을 경우
			message = "현재 회원님은 이용중인 자리가 있습니다.";
			result = "fail";
		} else if (isMemberUsingSpace == 1) {
			message = "현재 회원님은 이용중인 공간이 있습니다.";
			result = "fail";
		} else {
			// 이용 가능한 좌석이며, 현재 회원이 이용 중인 좌석이 없을 경우
			// -1 : 이용 등록 성공
			int useSeatResult = service.useSeat(libSeat.getSeatNo(), memberNo);

			// 이용 등록 성공한 경우
			if (useSeatResult == -1) {
				message = "좌석 이용 등록 성공!";
				result = "success";

				// DB의 열람실 번호와 유저가 보는 열람실 번호가 다르다.
				// -> 해결하기 위해 다음과 같은 SQL을 수행한다.
				int cacRealSeatNo = service.getCacRealSeatNo(libSeat.getSeatNo());

				// 사용자가 선택한 열람실 좌석의 db상 번호
				int dbSeatNo = libSeat.getSeatNo();

				// 사용자에게 보여줄 열람실 번호
				userSeatNo = dbSeatNo - cacRealSeatNo;

				// 회원이 이용 중인 자리 session에 저장하기
				Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session
						.getAttribute("memberAndSeatSession");
				if (memberAndSeatSession == null) {
					memberAndSeatSession = new HashMap<>();
				}
				memberAndSeatSession.put(memberNo, userSeatNo);

				session.setAttribute("memberAndSeatSession", memberAndSeatSession);

				System.out.println("이용시작 회원번호 : 자리번호(DB): " + memberAndSeatSession);

			} else {
				message = "좌석 이용 실패...";
				result = "fail";
			}

		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("result", result);
		response.put("userSeatNo", userSeatNo);

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
	public Map<String, Object> extendSeat(HttpSession session, RedirectAttributes ra) {

		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		String message = null;
		int result = 0;

		// 세션에 저장된 현재 로그인된 회원의 자리 번호 가져오기(실제 이용 번호)
		Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session
				.getAttribute("memberAndSeatSession");

		// 실제 이용 번호
		int seatNo = memberAndSeatSession.get(memberNo);

		// DB의 seatNo2를 계산하기
		int cacRealSeatNo = service.getCacRealSeatNo(seatNo);
		int seatNo2 = seatNo + cacRealSeatNo;

		// 내가 연장하고자 하는 시간에 예약이 있다면 연장 불가 확인하기
		int checkOtherReservation = service.checkOtherReservation(seatNo, seatNo2);

		// 있다면 연장 불가
		if (checkOtherReservation == 1) {
			message = "해당 시간에 우선된 예약이 있어 자리 연장이 불가합니다.";
		}

		// 없다면 연장 기능 실행
		if (checkOtherReservation == 0) {

			// 나의 남은 연장 기회 확인하기
			int checkExtendChance = mapper.checkExtendChance(memberNo);
			if (checkExtendChance == 0) {
				message = "남은 연장 기회가 없습니다.";
			} else { // 연장 수행
				int doExtendSeat = service.extendSeat(memberNo);
				if (doExtendSeat == 1) {
					message = "연장에 성공했습니다";
					result = 1;
				} else {
					message = "연장 실패. 오류코드 extendSeat001. 관리자에게 문의하세요.";
				}
			}
		}

		ra.addFlashAttribute("message");

		Map<String, Object> response = new HashMap<>();
		response.put("result", result);
		response.put("message", message);
		
		return response;
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
			if (ifYouHaveAnyOtherReservation == 0) {
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

		if (terminal == 0) {
			int checkStartTime = service.checkStartTime(seatNo, startTime);
			if (checkStartTime == 1) {
				message = "현재 이용중인 자리의 종료 예정시간 이전엔 예약이 불가합니다.";
				terminal = 1;
				result.put("checkStartTime", message);
			}
		}

		// 7. 모든 조건을 통과했을 때 예약 수행
		if (terminal == 0) {
			// 예약 성공
			int seatBooking = service.seatBooking(memberNo, seatNo, startTime);

			if (seatBooking == 1) {
				message = "예약이 성공적으로 완료되었습니다.";
				success = true;
				result.put("seatBooking", message);
				result.put("success", success);
			}
			// 예약 실패 시
			// (모든 조건을 만족시켰으나 컬럼에 값이 입력되지 않은 경우)
			if (seatBooking == 0) {
				message = "예약에 실패했습니다.. 관리자에게 문의해 주세요.";
				result.put("seatBooking", message);
			}

		}

		result.put("success", success);
		result.put("message", message);
		ra.addFlashAttribute("message", message);

		return result;
	}

	// 나의 열람실 이용 정보 받아오기
	// 필요한 정보
	// 회원 번호

	// 보낼 정보 (map으로 묶어서 보내기)
	// 1. 이용 시작 시간
	// 2. 이용 종료 예정 시간
	// 3. 남은 연장 기회
	@PostMapping("/getMySeatInfo")
	@ResponseBody
	public Map<String, Object> getMySeatInfo(@SessionAttribute("loginMember") Member loginMember) {

		String message = null;
		int memberNo = loginMember.getMemberNo();

		// 정보를 가져올 service
		LibSeatDTO result = service.getMySeatInfo(memberNo);

		// 결과를 담아 보낼 map
		Map<String, Object> map = new HashMap<>();

		if (result == null) {
			message = "회원님은 열람실 이용 중이 아니세요..";
			map.put("message", message);
		}

		if (result != null) {
			
			
			map.put("startTime", result.getReadingStart());
			map.put("endTime", result.getReadingDone());
			map.put("readingExtend", result.getReadingExtend());
			
			
		}
		return map;

	}

	// 열람실 예약 정보 불러오기
	@PostMapping("checkMySeatReservation")
	@ResponseBody
	public Map<String, Object> checkMySeatReservation(
	        @SessionAttribute("loginMember") Member loginMember) {
	    
	    int memberNo = loginMember.getMemberNo();
	    Map<String, Object> result = service.checkMySeatReservation(memberNo);
	    
	    System.out.println("Controller Result: " + result);  // 로그 출력
	    
	    return result;
	}
	
	// 열람실 예약 취소하기
	@PostMapping("cancleSeatBooking")
	@ResponseBody
	public Map<String, Object> cancleSeatBooking(@SessionAttribute("loginMember") Member loginMember) {
		int memberNo = loginMember.getMemberNo();
		String message = null;
		
		int result = service.cancleSeatBooking(memberNo);
		
		if(result == 1) {
			message = "예약이 취소되었습니다.";
		}
		if(result == 0) {
			message = "예약을 취소할 수 없습니다.";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		System.out.println("Controller Result: " + map);
		
		
		return map;
	}	

	@PostMapping("banAllSeatUsers")
	@ResponseBody
	public String banAllSeatUsers() {
		
		String message = null;
		
		int result = service.banAllSeatUsers();
		
		if(result == 0) {
			message = "모든 회원의 열람실 이용이 종료되었습니다.";
		}
		if(result == 1) {
			message = "회원의 열람실 강제 종료 실패..";
		}
		
		System.out.println("Controller Result: " + message);
		
		
		return message;
	}
	
	
	
}
