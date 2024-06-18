package edu.kh.cooof.gym.myPage.model.service;

import java.util.List;

import edu.kh.cooof.gym.myPage.model.dto.GymMyPage;

public interface gymMyPageService {


	GymMyPage getPTInfo(int memberNo);

	List<GymMyPage> getAllPTRecords(int memberNo);

}
