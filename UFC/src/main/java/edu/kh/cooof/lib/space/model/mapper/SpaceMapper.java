package edu.kh.cooof.lib.space.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;

@Mapper
public interface SpaceMapper {

	// tbSpace 지우기
	void delTbSpace();

	// 편집내용 저장하기
	int saveManagement(SpaceDTO space);

	// 저장된 공간 정보 불러오기
	List<SpaceDTO> getAllSpaces();

	// 관리자 : 공간의 avail 수정하기
	int updateSpaceStatus(Map<String, Object> params);

	// 공간 이용하기
	int useSpace(Map<String, Object> params);

	// 공간 이용 가능여부 확인하기
	int checkAvail(int spaceNo);

	// 이용 요청한 회원이 현재 이용 중인 공간이 있는지 확인
	int memberSpaceUsingCheck(int memberNo);

	// 그만 이용하기
	int stopUsingSpace(Map<String, Object> params);

	// 공간 이용 기록 지우기
	int deleteRentSpace(Map<String, Object> params);

	// 자리 연장 기회 카운트
	int countExtend(int memberNo);

	// 연장 성공 시 연장 기회 차감
	int updateRentSpace(int memberNo);

	// 자리 연장하기
	int extendUseSpace(int memberNo);

	// 예약하고자 하는 공간에 다른 예약이 있는지 확인하기
	Integer checkOtherReservation(Map<String, Object> params);

	// 나에게 다른 예약이 있는지 확인하기
	int checkMyReserVation(int memberNo);

	// 예약 시작 시간이 해당 좌석 종료 예정 시간 이후인지 확인
	Integer checkStartTime(Map<String, Object> params);

	// 자리 예약하기
	Integer bookSpace(Map<String, Object> params);

	// 공간 이용 시작 시간, 종료시간, 남은 연장기회 가져오기
	SpaceDTO rentSpaceInfo(int memberNo);

}
