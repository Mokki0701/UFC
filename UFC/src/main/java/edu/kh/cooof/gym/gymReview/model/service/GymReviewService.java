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

	/** 글쓰기 제출값 넣기
	 * @param gymReview
	 * @return
	 */
	int insertGymWrite(GymReview gymReview);

	int getGymReviewCount();

	List<GymReview> getGymReviews(int currentPage, int limit);

	

}
