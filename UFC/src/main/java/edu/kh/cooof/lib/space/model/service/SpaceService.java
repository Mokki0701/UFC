package edu.kh.cooof.lib.space.model.service;

import java.util.List;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;

public interface SpaceService {

	// 편집 저장하기
	int saveSpaceManagement(List<SpaceDTO> spaceList);

	// 저장된 공간 정보 불러오기
	List<SpaceDTO> getAllSpaces();

}
