package edu.kh.cooof.lib.seat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.kh.cooof.lib.seat.model.service.LibSeatService;
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
				Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session.getAttribute("memberAndSeatSession");
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

	// 열람실 이용 종료하기
    @PostMapping("/stopUsingSeat")
    public ResponseEntity<Map<String, String>> stopUsingSeat(@SessionAttribute("loginMember") Member loginMember, HttpSession session) {
        int memberNo = loginMember.getMemberNo();
        int seatNo = service.stopUsingSeat(memberNo);

        String message;
        String result;
        if (seatNo > 0) {
            // 세션에서 회원과 자리 정보를 담은 맵 가져오기
            Map<Integer, Integer> memberAndSeatSession = (Map<Integer, Integer>) session.getAttribute("memberAndSeatSession");
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
}