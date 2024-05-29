package edu.kh.cooof.gym.gymReview.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;
import edu.kh.cooof.gym.gymReview.model.mapper.GymReviewMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymReviewServiceImpl implements GymReviewService {

	private final GymReviewMapper mapper;
	
	@Override
	public List<GymReview> getAllGym() {
		return mapper.getAllGym();
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

	@Override
	public List<GymReview> getGymReviews(int currentPage, int limit) {
	    int offset = (currentPage - 1) * limit;
	    Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("limit", limit);
	    paramMap.put("offset", offset);

	    return mapper.selectGymReviews(paramMap);
	}
	
}
