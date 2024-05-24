package edu.kh.cooof.lib.space.model.mapper;

import java.util.List;

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

	// 공간 이용하기
	int useSpace(int memberNo, int spaceNo);

}
