package edu.kh.cooof.gym.gymReview.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.cooof.gym.gymReview.model.dto.GymReview;
import edu.kh.cooof.gym.gymReview.model.service.GymReviewService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GymReviewController {

	private final GymReviewService service;
	
	@GetMapping("reviews/gymReview")
	public String reviews(Model model) {
		
		List<GymReview> gymList = service.getAllGym();
		
		model.addAttribute("gymList", gymList);
		
		
		return "gym/gymReview/gymReview";
	}
	
	
	
	@GetMapping("reviews/view")
	public String viewGym(@RequestParam("gymNo") int gymNo,
			Model model) {
		
	    GymReview gymReview = service.getGymByNo(gymNo); // gymNo에 해당하는 GymReview 객체를 가져옴
	    model.addAttribute("gymReview", gymReview); // 뷰로 전달할 모델에 gymReview 추가
	   
	    return "gym/gymReview/gymView"; // gymView.html을 반환
	}
	
}
