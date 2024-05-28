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

	

}
