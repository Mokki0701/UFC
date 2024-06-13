package edu.kh.cooof.lib.returnTimer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.cooof.lib.space.model.dto.DateTimeRequest;
import edu.kh.cooof.lib.space.model.service.SpaceService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("lib")
@RequiredArgsConstructor
public class LibreturnTimerController {

	private final SpaceService service;
	
	// 종료 시간에 따른 알람 및 종료
	@PostMapping("doneTimeCheck")
	@ResponseBody
	@Transactional
	public void doneTimeCheck(@RequestBody DateTimeRequest request) {

		System.out.println("요청이 왓읍니다");
		
		// body에서 현재 시간 가져오기(스트링타입)
		String currentTimeStr = request.getDateTime();

		// String을 LocalDateTime으로 변경(왜? : 5분 후를 구하기 위해서)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, formatter);

		// 5분 더한 시간
		LocalDateTime curPlus5Min = currentTime.plusMinutes(5);

		// 5분 더한 시간(스트링 타입)
		String curPlus5MinStr = curPlus5Min.format(formatter);

		// 현재 공간을 이용 중인 memberNo를 가져오기
		List<Integer> getSpaceUserNo = service.getSpaceUserNo();

		// 공간 이용 중인 member마다 실행하기
		for (int userNo : getSpaceUserNo) {
			// memberNo에 맞는 공간 이용 종료 시간 가져오기
			String spaceUserDoneTime = service.spaceUserDoneTime(userNo);

			// 현재 시간에 5분을 더한 시간이 같은 회원에게 메세지를 보낸다.
			if (curPlus5MinStr.equals(spaceUserDoneTime)) {
				System.out.println(userNo + "번 회원님, 공간 이용시간 5분 남았습니다.");
				// userNo를 기준으로 메세지를 보내면 됩니다.
			}

			// 현재 시간과 spaceUserDoneTime이 같은 유저에게 delete, update.
			if (currentTimeStr.equals(spaceUserDoneTime)) {

				service.updateSpaceToAvailable(userNo);
				service.getOut(userNo);

				System.out.println(userNo + "번 회원님, 공간 대여 이용 시간이 만료되었습니다.");
				// 여기서 userNo를 기준으로 공간대여 종료 메세지를 보내면 됩니다.
			}
		}

		// 현재 열람실을 이용중인 memberNo가져오기
		List<Integer> getSeatUserNo = service.getSeatUserNo();

		for (int seatUserNo : getSeatUserNo) {

			String seatUserDoneTime = service.seateUserDoneTime(seatUserNo);

			if (curPlus5MinStr.equals(seatUserDoneTime)) {

				// 여기서 알람을 보내면 됩니다.
			}

			if (currentTimeStr.equals(seatUserDoneTime)) {

				service.setSeatAvailable(seatUserNo);
				service.getOutFromSeat(seatUserNo);

				// 여기서 알람을 보내면 됩니다. 매개변수 : seatUserNo
			}

		}

	}
}
