package edu.kh.cooof.lib.space.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;

@Mapper
public interface SpaceMapper {

	// tbSpace 지우기
	void delTbSpace();
	
	// 편집내용 저장하기
	int saveManagement(SpaceDTO space);

}
