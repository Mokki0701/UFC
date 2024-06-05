package edu.kh.cooof.lib.seat.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.seat.model.mapper.LibSeatMapper;
import edu.kh.cooof.member.model.dto.Member;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LibSeatServiceImpl implements LibSeatService {
	@Autowired
	private LibSeatMapper mapper;

	// 열람실 편집 현황 불러오기
	@Override
	public List<LibSeatDTO> getAllSeats() {
		List<LibSeatDTO> seats = mapper.getAllSeats();
		return seats;
	}

	// 좌석 배치 편집 현황 저장하기
	@Override
	public void saveSeats(List<LibSeatDTO> seats) {
		mapper.deleteRentSeat();
		mapper.deleteAllSeats();
		for (LibSeatDTO seat : seats) {
			log.debug("Inserting seat: {}", seat);
			mapper.insertSeat(seat);
		}
	}

	// 회원이 이용 중인 좌석이 있는지 확인하기
	@Override
	public int isMemberUsing(int memberNo) {
		return mapper.isMemberUsing(memberNo) == 1 ? 1 : -1;
	}

	// 좌석 이용하기
	@Override
	public int useSeat(int seatNo2, int memberNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("seatNo2", seatNo2);
		params.put("memberNo", memberNo);

		return mapper.useSeat(params);
	}

	@Override
	public LibSeatDTO getSeatUsageByMemberNo(int memberNo) {
		return mapper.getSeatUsageByMemberNo(memberNo);
	}

	// 열람실 이용 종료하기
	@Override
	@Transactional
	public int stopUsingSeat(int memberNo) {
		int seatNo = mapper.getMemberUsingSeat(memberNo);
		if (seatNo > 0) {
			mapper.clearMemberFromSeat(seatNo);
			mapper.clearConditionFromSeat(seatNo);
		}
		return seatNo;
	}

	// 열람실 연장하기
	@Override
	public int extendSeat(int memberNo ) {
		return mapper.extendSeat(memberNo);
		
	}

	// 현재 다른사람이 이용 중인 열람실을 예약했을 때,
	// 열람실을 예약한 시간과 종료예정시간이 겹치는지 확인
	@Override
	public int checkStartTime(int seatNo, String startTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("seatNo", seatNo);
		params.put("startTime", startTime);
		
		int checkTime = mapper.checkStartTime(params);
		if (checkTime == 1) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public int seatBooking(int memberNo, int seatNo, String startTime ) {

		Map<String, Object> params = new HashMap<>();
		params.put("seatNo", seatNo);
		params.put("startTime", startTime);
		params.put("memberNo", memberNo);
		
		String message = null;
		int result = 0;
		
		int checkTime = mapper.seatBooking(params);
		if (checkTime == 1) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}
	
	// 유저의 자리 번호와 db의 자리번호 맞추기
	@Override
	public int getCacRealSeatNo(int seatNo) {
		
		int getCacRealSeatNo = mapper.getCacRealSeatNo(seatNo);
		
		return getCacRealSeatNo;
	}
	
	// 나의 자리 이용 정보 받아오기
	@Override
	public LibSeatDTO getMySeatInfo(int memberNo) {
		
		return mapper.getMySeatInfo(memberNo);
	}
	
	// 내 자리에 예약이 있는지 확인하기
	@Override
	public int checkOtherReservation(int seatNo, int seatNo2) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("seatNo", seatNo);
		params.put("seatNo2", seatNo2);
		
		return mapper.checkOtherReservation(params);
	}
	
	// 나의 열람실 예약 확인하기
	@Override
	public Map<String, Object> checkMySeatReservation(int memberNo) {
		
		Map<String, Object> result = mapper.checkMySeatReservation(memberNo);
	    System.out.println("Mapper Result: " + result);  // 로그 출력
		
		return result;
		
	}
}