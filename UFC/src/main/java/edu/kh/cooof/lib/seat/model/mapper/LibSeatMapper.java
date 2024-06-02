package edu.kh.cooof.lib.seat.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;

@Mapper
public interface LibSeatMapper {

	// 현재 좌석 불러오기
	List<LibSeatDTO> getAllSeats();

	// 좌석 편집 현황 저장하기
	// 1. 모든 자리 삭제
	void deleteAllSeats();

	// 2. rentSeat 삭제하기
	void deleteRentSeat();

	// 3. 편집 저장하기
	void insertSeat(LibSeatDTO seat);

	// 좌석 이용하기
	// 1. 회원이 사용 중인 좌석 있는지 확인
	int isMemberUsing(int memberNo);

	// 2. 좌석 이용하기
	int useSeat(Map<String, Object> params);

	// 열람실 이용 종료하기
	int getMemberUsingSeat(int memberNo);

	void clearMemberFromSeat(int seatNo);

	void clearConditionFromSeat(int seatNo);

	@Select("SELECT READING_START, READING_DONE, READING_EXTEND FROM RENT_SEAT WHERE MEMBER_NO = #{memberNo}")
	LibSeatDTO getSeatUsageByMemberNo(int memberNo);

	// 열람실 연장하기
	int extendSeat(int memberNo);

	// 현재 좌석의 상태 확인하기
	int chckSeatConditon(int seatNo);

	// 현재 다른사람이 이용 중인 열람실을 예약했을 때,
	// 열람실을 예약한 시간과 종료예정시간이 겹치는지 확인
	int checkStartTime(Map<String, Object> params);

	// 열람실 예약 실행
	int seatBooking(Map<String, Object> params);


}