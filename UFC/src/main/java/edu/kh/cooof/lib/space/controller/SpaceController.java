package edu.kh.cooof.lib.space.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import edu.kh.cooof.lib.seat.model.service.LibSeatService;
import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.dto.SpaceRentInfoDTO;
import edu.kh.cooof.lib.space.model.mapper.SpaceMapper;
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

	private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);

	private final SpaceService service;
	// controller에서 mapper를 바로 호출하지 않는 이유..
	// -> service에서 처리 할 수 있는 흐름을 제어하지 못함
	// 간단한 결과 처리는 바로 mapper 호출 가능
	private final SpaceMapper mapper;
	private final LibSeatService seatService;

	// 관리자 : 공간 편집 저장하기
	@PostMapping("/saveSpaceManagement")
	public ResponseEntity<Integer> saveSpaceManagement(@RequestBody List<SpaceDTO> spaceList) {
		int insertedCount = service.saveSpaceManagement(spaceList);
		return ResponseEntity.ok(insertedCount);
	}

	// 관리자 : 공간의 avail 여부 수정하기
	@PostMapping("/updateSpaceStatus")
	public ResponseEntity<Map<String, String>> updateSpaceStatus(@RequestBody Map<String, Integer> request) {
		int spaceNo = request.get("spaceNo");
		int status = request.get("status");

		String result = service.updateSpaceStatus(spaceNo, status);

		Map<String, String> response = Map.of("message", result);

		if ("성공적으로 업데이트되었습니다.".equals(result)) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
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
	public String postMethodName(HttpSession session, HttpServletRequest request, @RequestParam("spaceNo") int spaceNo,
			SpaceDTO space, Model model, RedirectAttributes ra) {

		// 공간의 대여 현황 검사하기
		// 전달 받은 공간 번호 중 space_avail 이 0 인 컬럼 조회
		// 결과 1 : 대여 가능, 결과 0 : 빌리기 불가
		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		int spaceAvail = mapper.checkAvail(spaceNo);
		int isMemberUsing = seatService.isMemberUsing(memberNo);

		String message = null;
		String path = request.getHeader("Referer"); // 이전 페이지 URL 가져오기

		if (loginMember == null) {
			message = "로그인 후 이용해 주세요";
			path = "redirect:/"; // 메인 페이지로 리다이렉트
			// 나중에 로그인 페이지로 리다이렉트 시키기
		}

		// 회원이 현재 이용중인 열람실이 있을 경우
		if (isMemberUsing == 1) {
			message = "열람실을 이용 중이신 회원은 공간 대여가 불가능합니다.";
			System.out.println("해당 회원은 열람실 이용 중");
			// 열람실 이용으로 이동시킬까?
			path = "redirect:" + path; // 현재 페이지로 리다이렉트
		}

		// 회원이 선택한 공간이 현재 이용 중일 경우
		if (loginMember != null && spaceAvail == 0) {
			message = "선택하신 공간은 다른 사람이 이용 중입니다.";
			path = "redirect:" + path;
			System.out.println("현재 다른 사람이 이용 중");
		}

		// 로그인 되어 있고, 이용 가능한 공간이며, 이용중인 열람실이 없을 경우
		if (loginMember != null && spaceAvail != 0 && isMemberUsing != 1) {

			// 현재 회원이 이용 중인 공간이 있는지 확인
			int memberSpaceUsingCheck = mapper.memberSpaceUsingCheck(memberNo);

			// 현재 이용 중인 공간 이 있을 경우
			if (memberSpaceUsingCheck == 1) {
				message = "회원님은 현재 이용 중인 공간이 있습니다.";
				System.out.println("해당 회원은 현재 이용 중인 공간이 있음.");
				// 집으로 가라
				path = "redirect:" + path;

			} else {

				// 회원이 현재 이용 중인 공간 없으면서 빈 공간을 선택한 경우
				// == 이용 가능한 경우

				int letUseSpace = service.useSpace(memberNo, spaceNo);
				System.out.printf("letUseSpace : %d%n", letUseSpace);
				message = "이용 요청이 처리되었습니다.";
				System.out.println("이용요청처리완료");

				// 공간 이용 정보를 map에 담아 session으로 올리기
				Map<Integer, Integer> memberAndSpaceSession = (Map<Integer, Integer>) session
						.getAttribute("memberAndSpaceSession");

				if (memberAndSpaceSession == null) {
					memberAndSpaceSession = new HashMap<>();
				}
				memberAndSpaceSession.put(memberNo, spaceNo);

				session.setAttribute("memberAndSpaceSession", memberAndSpaceSession);

				System.out.println("이용 시작 회원 번호 : 자리번호(DB) == " + memberAndSpaceSession);

				path = "redirect:" + path; // 현재 페이지로 리다이렉트
			}
		}

		ra.addFlashAttribute("message", message);

		return path;
	}

	// 공간 그만 사용하기
	@PostMapping("/stopUsingSpace")
	public String stopUsingSpace(Model model, HttpSession session, HttpServletRequest request, RedirectAttributes ra) {
		String referer = request.getHeader("Referer"); // 이전 페이지 URL 가져오기
		String message = null;

		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		Map<Integer, Integer> memberAndSpaceSession = (Map<Integer, Integer>) session
				.getAttribute("memberAndSpaceSession");

		if (memberAndSpaceSession != null && memberAndSpaceSession.containsKey(memberNo)) {
			int curUsingSpaceNo = memberAndSpaceSession.get(memberNo);
			int stopUseSpace = service.stopUsingSpace(memberNo, curUsingSpaceNo);

			if (stopUseSpace == 2) {
				message = "공간 대여 종료 완료.";
				memberAndSpaceSession.remove(memberNo); // 세션에서 공간 정보 제거
			} else {
				message = "공간 대여 종료 실패. 관리자에게 문의하세요.";
			}
		} else {
			message = "현재 사용 중인 공간이 없습니다.";
		}

		ra.addFlashAttribute("message", message);

		// 현재 페이지로 리다이렉트
		return "redirect:" + (referer != null ? referer : "/defaultPage"); // referer가 없을 경우 기본 페이지로 리다이렉트
	}

	// 자리 연장하기
	@PostMapping("/extendUseSpace")
	public String extendUseSpace(HttpSession session, Model model, HttpServletRequest request, RedirectAttributes ra) {

		String path = request.getHeader("Referer"); // 이전 페이지 URL 가져오기
		String message = null;
		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();

		// memberAndSpaceSession에 담겨 있는 객체 사용할거임
		Map<Integer, Integer> memberAndSpaceSession = (Map<Integer, Integer>) session
				.getAttribute("memberAndSpaceSession");

		// 빌린 자리가 없을 경우
		if (!memberAndSpaceSession.containsKey(memberNo)) {

			message = "회원님은 현재 대여 중인 공간이 없습니다.";
			System.out.println("현재 대여 중인 공간 없음");
			path = "redirect:" + path; // 현재 페이지로 리다이렉트

		}

		// 빌린 자리가 있을 경우
		else {

			// 연장 기회 카운트
			int countExtend = mapper.countExtend(memberNo);

			// 연장 기회가 남아 없을 경우
			if (countExtend != 1) {
				message = "남은 연장 기회가 없습니다..";
				System.out.println("연장 기회 없음");
			}

			// 연장 기회가 있을 경우
			if (countExtend == 1) {
				// 연장 기회 차감
				int updateRentSpace = mapper.updateRentSpace(memberNo);
				// 자리 연장 수행
				int extend = mapper.extendUseSpace(memberNo);

				// 결과 값 처리
				if (extend == 1) {
					message = "자리 연장 성공.";
					System.out.println("자리 연장 성공");
				}

			}

		}

		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}

	// 공간 예약하기
	@PostMapping("bookSpace")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> bookSpace(@RequestBody SpaceDTO bookingRequest, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		int spaceNo = bookingRequest.getSpaceNo();
		int spaceExtend = bookingRequest.getSpaceExtend();
		String startTime = bookingRequest.getStartTime();

		// 예약 성공 시 예약 정보를 담을 세션 객체 생성 또는 가져오기
		Map<String, Object> bookingSpaceSession = (Map<String, Object>) session.getAttribute("bookingSpaceSession");
		if (bookingSpaceSession == null) {
			bookingSpaceSession = new HashMap<>();
		}

		Map<String, Object> response = new HashMap<>();
		String message = null;
		boolean success = false;

		log.debug("Starting booking process for memberNo: {}, spaceNo: {}, startTime: {}", memberNo, spaceNo,
				startTime);

		try {
			// 1. 회원이 현재 이용 중인 공간이 있는지 확인하기
			int memberSpaceUsingCheck = mapper.memberSpaceUsingCheck(memberNo);
			log.debug("memberSpaceUsingCheck result: {}", memberSpaceUsingCheck);
			if (memberSpaceUsingCheck == 1) {
				message = "회원님은 현재 이용 중인 공간이 있습니다.";
			} else {
				// 2. 회원이 현재 이용 중인 열람실 있는지 확인하기
				int isMemberUsing = seatService.isMemberUsing(memberNo);
				log.debug("isMemberUsing result: {}", isMemberUsing);
				if (isMemberUsing == 1) {
					message = "회원님은 현재 이용 중인 열람실이 있습니다.";
				} else {
					// 3. 나에게 다른 예약이 있을 때 확인
					int ifYouHaveAnyOtherReservation = service.ifYouHaveAnyOtherReservation(memberNo);
					log.debug("ifYouHaveAnyOtherReservation result: {}", ifYouHaveAnyOtherReservation);
					if (ifYouHaveAnyOtherReservation == 0) {
						message = "회원님은 이미 다른 예약이 있으세요.";
					} else if (ifYouHaveAnyOtherReservation == 2) {
						message = "예약실패.. 아래 코드와 함께 관리자에게 문의하세요. 오류코드 :spaceBookingFailure0003";
					} else {
						// 4. 같은 시간에 다른 예약 건이 있는지 확인
						int checkOtherReservation = service.checkOtherReservation(spaceNo, startTime);
						log.debug("checkOtherReservation result: {}", checkOtherReservation);
						if (checkOtherReservation == 1) {
							message = "요청하신 예약 시간에 다른 예약이 있습니다.";
						} else {
							// 5. 현재 공간의 이용 가능 여부 확인
							int spaceAvail = mapper.checkAvail(spaceNo);
							log.debug("checkAvail result: {}", spaceAvail);
							if (spaceAvail == 1) { // 공간이 사용 가능하면 1

								int checkStartTime = service.checkStartTime(spaceNo, startTime);
								log.debug("checkStartTime result: {}", checkStartTime);

								if (checkStartTime == 1) {
									message = "해당 공간은 이용 불가합니다.";

								} else {
									int bookSpace = service.bookSpace(memberNo, spaceNo, startTime);
									log.debug("bookSpace result: {}", bookSpace);
									if (bookSpace == 1) {
										message = "공간 예약 성공!";
										success = true;

										bookingSpaceSession.put("memberNo", memberNo);
										bookingSpaceSession.put("spaceNo", spaceNo);
										bookingSpaceSession.put("startTime", startTime);
										bookingSpaceSession.put("spaceExtend", spaceExtend);
										session.setAttribute("bookingSpaceSession", bookingSpaceSession); // 세션에 저장
										response.put("bookingSpaceSession", bookingSpaceSession); // 응답에 세션을 담기
									} else if (bookSpace == 0) {
										message = "공간 예약 실패 : 오류 코드 : spaceBookingFailure0000";
									} else if (bookSpace == 2) {
										message = "공간 예약 실패 : 오류 코드 : spaceBookingFailure0002";
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			message = "예약 처리 중 오류가 발생했습니다.";
			log.error("Error during booking space: ", e);
		}

		response.put("message", message != null ? message : "예약에 실패했습니다."); // message가 null인 경우 기본 메시지 설정
		response.put("success", success);

		log.debug("Booking response: {}", response);

		return ResponseEntity.ok(response);
	}

	// 공간 대여 현황 표시하기
	@PostMapping("checkMySpace")
	@ResponseBody
	public Map<String, Object> checkMySpace(@SessionAttribute("loginMember") Member loginMember, Model model) {
		int memberNo = loginMember.getMemberNo();
		String message = null;

		// 응답을 담을 map 객체 생성
		Map<String, Object> result = new HashMap<>();

		// 공간 정보 가져오기
		SpaceDTO rentSpaceInfo = service.rentSpaceInfo(memberNo);

		// 가져온 공간 정보가 없다면
		if (rentSpaceInfo == null) {
			message = "회원님은 현재 이용 중인 공간이 없습니다";
			result.put("message", message);
		}

		if (rentSpaceInfo != null) {

			result.put("startTime", rentSpaceInfo.getStartTime());
			result.put("endTime", rentSpaceInfo.getEndTime());
			result.put("remainingExtensions", rentSpaceInfo.getSpaceExtend());

		}

		return result;
	}

	// 공간 예약 내역 확인하기
	@PostMapping("checkMySpaceReservation")
	@ResponseBody
	public Map<String, Object> checkMySpaceReservation(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		int memberNo = loginMember.getMemberNo();
		String message = null;

		// 응답을 담을 map 객체 생성
		Map<String, Object> result = new HashMap<>();

		// 공간 정보 가져오기
		SpaceDTO spaceReservationInfo = service.spaceReservationInfo(memberNo);

		// 가져온 공간 정보가 없다면
		if (spaceReservationInfo == null) {
			message = "공간 예약 내역이 없습니다.";
			result.put("message", message);
		}

		if (spaceReservationInfo != null) {

			result.put("spaceNo", spaceReservationInfo.getSpaceNo());
			result.put("startBooking", spaceReservationInfo.getStartBooking());

		}

		return result;
	}

	// 공간 예약 취소하기
	@PostMapping("cancleSpceBooking")
	@ResponseBody
	public int cancleSpceBooking(@SessionAttribute("loginMember") Member loginMember, Model model) {
		int memberNo = loginMember.getMemberNo();

		return mapper.cancleSpceBooking(memberNo);
	}

	// 종료 시간 체크
	@GetMapping("/spaceDoneTime")
	@ResponseBody
	public List<Map<String, Object>> spaceDoneTime() {
		int repeat = service.countSpace();
		List<Map<String, Object>> responseList = new ArrayList<>();

		for (int i = 0; i < repeat; i++) {
			int spaceNo = i + 1;
			Map<String, Object> spaceDoneTime = service.spaceDoneTime(spaceNo);

			// 로그 추가
			logger.info("Space number: " + spaceNo);

			if (spaceDoneTime == null) {
				spaceDoneTime = new HashMap<>();
				spaceDoneTime.put("spaceNo2", spaceNo);
				spaceDoneTime.put("spaceDone", null);
			} else {
				spaceDoneTime.put("spaceNo2", spaceNo);
			}

			logger.info("Space done time details: " + spaceDoneTime);

			responseList.add(spaceDoneTime);
		}

		// 응답 로그 추가
		logger.info("Response List: " + responseList);

		return responseList;
	}

	// 종료 시간에 따른 알람 및 종료
	@GetMapping("/doneTimeCheck")
	@ResponseBody
	public Map<String, Object> doneTimeCheck() {

		Map<String, Object> response = new HashMap<>();

		// 기준이 될 memberNo list 가져오기
		List<Integer> spaceUserNo = service.getSpaceUserNo();

		// 결과 값이 있으면 실행
		if (!spaceUserNo.isEmpty()) {

			// 리스트 안의 memberNo별로 map을 가져오고
			for (int userNo : spaceUserNo) {

				Map<String, Object> spaceDoneChecker = service.spaceUserDoneTime(userNo);
				if (spaceDoneChecker == null || spaceDoneChecker.isEmpty())
					continue;

				logger.info("Space done checker: " + spaceDoneChecker);

				// 가져온 map(userNo, doneTime) 에 따라서 다음을 실행
				String doneTimeStr = (String) spaceDoneChecker.get("spaceDone");
				logger.info("Done time: " + doneTimeStr);

				// 현재 시간
				LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

				// doneTime을 LocalDateTime으로 변환
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
				LocalDateTime doneTime = doneTime = LocalDateTime.parse(doneTimeStr, formatter)
						.truncatedTo(ChronoUnit.MINUTES);

				// 현재 시간과 doneTime을 비교
				long minutesUntilDone = ChronoUnit.MINUTES.between(currentTime, doneTime);
				logger.info("Minutes until done: " + minutesUntilDone);

				// 5분 전 알람 보내기
				if (minutesUntilDone == 5) {
					System.out.printf("종료시간 5분 전이다. 회원 번호 : " + userNo);
					// 여기에서 알람을 보내면 됩니다.
					// seatWebSocketHandler.sendMessageToUser(userNo, "공간 이용 종료 시간이 5분 남았습니다.");
				}

				// 이용 종료시키기
				if (minutesUntilDone <= 0) {
					int getOut = service.getOut(userNo);
					// 여기에서 알람을 보내면 됩니다.
					System.out.printf(userNo + "번 회원의 공간 이용이 종료됨");
				}
			}
		}

		response.put("status", "done");
		return response;
	}

}
