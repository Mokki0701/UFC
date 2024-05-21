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
		log.debug("Deleting all seats...");
		mapper.deleteRentSeat();
		mapper.deleteAllSeats();
		log.debug("Inserting new seats...");
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
}