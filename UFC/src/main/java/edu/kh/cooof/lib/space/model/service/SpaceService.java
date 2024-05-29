package edu.kh.cooof.lib.space.model.service;

import java.util.List;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;

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

	// 내가 예약하려는 시간과 다른 사람이 이용 중인 시간이 겹치는지 확인
	int checkStartTime(int spaceNo, String startTime);

	// 공간 예약하기
	int bookSpace(int memberNo, int spaceNo, String startTime);

}
