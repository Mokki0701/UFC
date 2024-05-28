package edu.kh.cooof.gym.gymReview.model.service;

import java.util.List;

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
}
