package edu.kh.cooof.gym.trainerSelect.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.service.TrainerSelectService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainerSelect")

public class TrainerSelectController {

	private final TrainerSelectService service;
	
	// 트레이너 선택 페이지
	@GetMapping("/trainerSelect")
	public String trainerSelect(
			Model model,
			HttpSession session,
			RedirectAttributes ra) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;	
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "로그인 후 이용해 주세요.");
			path = "/gym/gymMain";
		} else {
			List<Trainer> trainers = service.getAllTrainers();	
			model.addAttribute("trainers", trainers);
			path = "gym/trainerSelect/trainerSelect";
		}
		
	    return path;
	}
	
	@GetMapping("trainerPrice")
	public String trainerPrice(
			@RequestParam("price") int price,
			Model model){
		List<Trainer> trainers = service.getAllTrainers();
		
		model.addAttribute("trainerPrice", price);
	    model.addAttribute("trainers", trainers);
		return "gym/trainerSelect/trainerPrice";
	}
	
    
	
	
	
	
	
}
