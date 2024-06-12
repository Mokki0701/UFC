package edu.kh.cooof.lib.space.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.dto.SpaceRentInfoDTO;

public interface SpaceService {

	// 편집 저장하기
	int saveSpaceManagement(List<SpaceDTO> spaceList);

	// 관리자 : 공간의 avail 수정하기
	String updateSpaceStatus(int spaceNo, int status);

	// 저장된 공간 정보 불러오기
	List<SpaceDTO> getAllSpaces();

	// 공간 이용하기
	int useSpace(int memberNo, int spaceNo);

	// 그만 이용하기
	int stopUsingSpace(int memberNo, int curUsingSpaceNo);

	// 공간에 다른 예약이 있는지 확인
	int checkOtherReservation(int spaceNo, String startTime);

	// 내가 현재 예약한 공간이 있는지 확인
	int ifYouHaveAnyOtherReservation(int memberNo);

	// 내가 예약하려는 시간과 다른 사람이 이용 중인 시간이 겹치는지 확인
	int checkStartTime(int spaceNo, String startTime);

	// 공간 예약하기
	int bookSpace(int memberNo, int spaceNo, String startTime);

	// 공간 이용 시작 시간, 종료시간, 남은 연장기회 가져오기
	SpaceDTO rentSpaceInfo(int memberNo);

	// 공간 예약 내역 확인하기
	SpaceDTO spaceReservationInfo(int memberNo);

	int countSpace();

	Map<String, Object> spaceDoneTime(int spaceNo);

	
	List<Integer> getSpaceUserNo();

	Map<String, Object> spaceUserDoneTime(int userNo);

	int getOut(int userNo);

}
