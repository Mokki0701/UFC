package edu.kh.cooof.gym.gymReview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;
import edu.kh.cooof.gym.gymReview.model.service.GymReviewService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GymReviewController {

	private final GymReviewService service;
	
	@GetMapping("reviews/gymReview")
	public String reviews(Model model) {
		
		GymReview review = new GymReview();
		int GymNo = review.getGymNo();
		
		
		model.addAttribute("review", review);
		
		return "gym/gymReview/gymReview";
	}
}
