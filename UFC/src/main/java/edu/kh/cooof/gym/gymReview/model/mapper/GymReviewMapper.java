package edu.kh.cooof.gym.gymReview.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;

@Mapper
public interface GymReviewMapper {

	List<GymReview> getAllGym(RowBounds rowBounds);

	GymReview getGymByNo(int gymNo);

	int insertGymWrite(GymReview gymReview);

	int countGymReviews();


	

}
