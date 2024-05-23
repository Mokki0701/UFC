package edu.kh.cooof.gym.gymReview.model.service;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.gymReview.model.mapper.GymReviewMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymReviewServiceImpl implements GymReviewService {

	private final GymReviewMapper mapper;
}
