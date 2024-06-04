package edu.kh.cooof.gym.gymReview.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;
import edu.kh.cooof.gym.gymReview.model.dto.gymPagination;
import edu.kh.cooof.gym.gymReview.model.mapper.GymReviewMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymReviewServiceImpl implements GymReviewService {

	private final GymReviewMapper mapper;
	
	@Override
	public List<GymReview> getAllGym(int currentPage, int limit) {
		int offset = (currentPage - 1) * limit;   // offset :  0~9  , currentPage 현재 페이지 , limit = 10
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return mapper.getAllGym(rowBounds);
	}
	
	@Override
	public GymReview getGymByNo(int gymNo) {
		
		return mapper.getGymByNo(gymNo);
	}
	
	@Override
	public int insertGymWrite(GymReview gymReview) {
		return mapper.insertGymWrite(gymReview);
	}
	
	@Override
    public int getGymReviewCount() {
        return mapper.countGymReviews();
    }

	
}
