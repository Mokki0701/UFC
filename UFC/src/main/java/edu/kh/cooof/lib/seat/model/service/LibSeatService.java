package edu.kh.cooof.lib.seat.model.service;

import java.util.List;

import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;

public interface LibSeatService {
	
	// 열람실 자리 불러오기
	List<LibSeatDTO> getAllSeats();
    
    // 열람실 편집 저장
    void saveSeats(List<LibSeatDTO> seats);

    // 열람실 이용하기
    // seatNo2 : db상 좌석
	int useSeat(int seatNo2, int memberNo);

	// 현재 회원이 이용 중인 좌석이 있는지 확인
	int isMemberUsing(int memberNo);
	
	// 이용 중인 좌석 종료하기
	int stopUsingSeat(int memberNo);


}
