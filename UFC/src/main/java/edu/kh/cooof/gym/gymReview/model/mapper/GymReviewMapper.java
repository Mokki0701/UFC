package edu.kh.cooof.gym.gymReview.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;

@Mapper
public interface GymReviewMapper {

	List<GymReview> getAllGym();

	GymReview getGymByNo(int gymNo);

	

}
