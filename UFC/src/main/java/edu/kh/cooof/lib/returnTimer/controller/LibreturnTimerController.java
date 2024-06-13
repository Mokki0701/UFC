package edu.kh.cooof.lib.returnTimer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.cooof.lib.book.model.mapper.BookLoanMapper;
import edu.kh.cooof.lib.returnTimer.model.dto.SeatAlerm;
import edu.kh.cooof.lib.returnTimer.model.dto.SpaceAlerm;
import edu.kh.cooof.lib.space.model.dto.DateTimeRequest;
import edu.kh.cooof.lib.space.model.service.SpaceService;
import edu.kh.cooof.member.model.dto.Member;

import jakarta.servlet.ServletContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("lib")
@RequiredArgsConstructor

public class LibreturnTimerController {

	private final SpaceService service;

	@Autowired
	private ServletContext servletContext;

	private final BookLoanMapper messageMapper;

	// 종료 시간에 따른 알람 및 종료
	@PostMapping("doneTimeCheck")
	@ResponseBody
	public Map<String, Object> doneTimeCheck(@RequestBody DateTimeRequest request) {

		System.out.println("요청이 왔습니다");
		// body에서 현재 시간 가져오기(스트링타입)
		String currentTimeStr = request.getDateTime();

		Member chiefMember = (Member) servletContext.getAttribute("chiefMember");

		// String을 LocalDateTime으로 변경(왜? : 5분 후를 구하기 위해서)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, formatter);

		List<SpaceAlerm> alermList = new ArrayList<>();
		// 5분 더한 시간
		LocalDateTime curPlus5Min = currentTime.plusMinutes(5);

		// 5분 더한 시간(스트링 타입)
		String curPlus5MinStr = curPlus5Min.format(formatter);

		// 현재 공간을 이용 중인 memberNo를 가져오기
		List<Integer> getSpaceUserNo = service.getSpaceUserNo();
		System.out.println("getSpaceUserNo 리스트 크기: " + getSpaceUserNo.size());

		// 공간 이용 중인 member마다 실행하기
		for (int userNo : getSpaceUserNo) {

			SpaceAlerm spaceAlerm = new SpaceAlerm();

			// memberNo에 맞는 공간 이용 종료 시간 가져오기
			String spaceUserDoneTime = service.spaceUserDoneTime(userNo);

			// 로그 출력
			System.out.println("currentTimeStr: " + currentTimeStr);
			System.out.println("curPlus5MinStr: " + curPlus5MinStr);
			System.out.println("spaceUserDoneTime: " + spaceUserDoneTime);

			Map<String, Integer> map = new HashMap<>();

			map.put("applyMemberNo", userNo);
			map.put("memberNo", chiefMember.getMemberNo());

			// 현재 시간에 5분을 더한 시간이 같은 회원에게 메세지를 보낸다.
			if (curPlus5MinStr.equals(spaceUserDoneTime)) {
				System.out.println(userNo + "번 회원님, 공간 이용시간 5분 남았습니다.");
				// userNo를 기준으로 메세지를 보내면 됩니다.

				map.put("checkNo", 5);
				messageMapper.transmitMessage(map);

				spaceAlerm.setSpaceUserNo(userNo);
				spaceAlerm.setCheckTime(0);
				alermList.add(spaceAlerm);

				continue;

			}

			// 현재 시간과 spaceUserDoneTime이 같은 유저에게 delete, update.
			if (currentTimeStr.equals(spaceUserDoneTime)) {

				try {
					service.updateSpaceToAvailable(userNo);
					service.getOut(userNo);
					System.out.println(userNo + "번 회원님, 공간 대여 이용 시간이 만료되었습니다.");

					// 여기서 userNo를 기준으로 공간대여 종료 메세지를 보내면 됩니다.
					map.put("checkNo", 6);
					messageMapper.transmitMessage(map);

					spaceAlerm.setSpaceUserNo(userNo);
					spaceAlerm.setCheckTime(1);
					alermList.add(spaceAlerm);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("공간 대여 종료 처리 중 예외 발생: " + e.getMessage());
				}

			}
		}

		// 현재 열람실을 이용중인 memberNo가져오기
		List<Integer> getSeatUserNo = service.getSeatUserNo();
		System.out.println("getSeatUserNo 리스트 크기: " + getSeatUserNo.size());

		List<SeatAlerm> seatAlermList = new ArrayList<>();

		for (int seatUserNo : getSeatUserNo) {

			SeatAlerm seatAlerm = new SeatAlerm();

			String seatUserDoneTime = service.seateUserDoneTime(seatUserNo);

			if (curPlus5MinStr.equals(seatUserDoneTime)) {

				seatAlerm.setSeatUserNo(seatUserNo);
				seatAlerm.setCheckTime(0);
				seatAlermList.add(seatAlerm);

				// 여기서 알람을 보내면 됩니다.
				continue;
			}

			if (currentTimeStr.equals(seatUserDoneTime)) {
				try {
					service.setSeatAvailable(seatUserNo);
					service.getOutFromSeat(seatUserNo);

					seatAlerm.setSeatUserNo(seatUserNo);
					seatAlerm.setCheckTime(1);
					seatAlermList.add(seatAlerm);

					System.out.println(seatUserNo + "번 회원님, 열람실 대여 이용 시간이 만료되었습니다.");

					// 여기서 알람을 보내면 됩니다. 매개변수 : seatUserNo

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("열람실 대여 종료 처리 중 예외 발생: " + e.getMessage());

					// 여기서 알람을 보내면 됩니다. 매개변수 : seatUserNo
				}

			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("spaceAlermList", alermList);
		map.put("seatAlermList", seatAlermList);

		return map;

	}

}