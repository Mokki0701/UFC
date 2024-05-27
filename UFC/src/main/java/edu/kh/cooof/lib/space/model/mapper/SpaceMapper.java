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
	

}
