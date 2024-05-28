package edu.kh.cooof.lib.space.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.mapper.SpaceMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class SpaceServiceImpl implements SpaceService {

	@Autowired
	private SpaceMapper mapper;

	// 공간 편집 저장하기
	@Transactional
	public int saveSpaceManagement(List<SpaceDTO> spaceList) {

		// space테이블의 기존 정보 지우기
		mapper.delTbSpace();

		// 편집 내용 저장하기
		int totalInserted = 0;
		for (SpaceDTO space : spaceList) {
			totalInserted += mapper.saveManagement(space);
		}
		return totalInserted;
	}

	// 저장된 공간 정보 불러오기
	@Override
	public List<SpaceDTO> getAllSpaces() {

		return mapper.getAllSpaces();
	}

	// 공간 이용하기
	@Override
	public int useSpace(int memberNo, int spaceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberNo", memberNo);
		params.put("spaceNo", spaceNo);
		return mapper.useSpace(params);
	}

	// 공간 그만 이용하기
	@Override
	public int stopUsingSpace(int memberNo, int curUsingSpaceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberNo", memberNo);
		params.put("curUsingSpaceNo", curUsingSpaceNo);

		int result1 = mapper.stopUsingSpace(params);
		if (result1 != 1) {
			System.out.println("그만 이용하기에서 실패");
		}
		int result2 = mapper.deleteRentSpace(params);
		if (result2 != 1) {
			System.out.println("기록 지우기에서 실패");
		}

		return result1 + result2;
	}

}
