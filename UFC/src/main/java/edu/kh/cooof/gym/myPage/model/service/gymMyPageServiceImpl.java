package edu.kh.cooof.gym.myPage.model.service;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.myPage.model.dto.GymMyPage;
import edu.kh.cooof.gym.myPage.model.mapper.gymMyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class gymMyPageServiceImpl implements gymMyPageService{
	
	private final gymMyPageMapper mapper;

	@Override
	public GymMyPage getPTInfo(int memberNo) {

		return mapper.selectPTInfo(memberNo);
	}
}
