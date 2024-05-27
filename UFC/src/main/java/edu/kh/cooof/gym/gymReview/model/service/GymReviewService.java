package edu.kh.cooof.gym.gymReview.model.service;

import java.util.List;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;

public interface GymReviewService {

	/** 짐 테이블 전체 조회
	 * @return
	 */
	List<GymReview> getAllGym();

	/** gy no 조회
	 * @param gymNo
	 * @return
	 */
	GymReview getGymByNo(int gymNo);

	

}
