package edu.kh.cooof.gym.trainerSelect.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.service.TrainerSelectService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainerSelect")

public class TrainerSelectController {

	private final TrainerSelectService service;
	
	@GetMapping("/trainerSelect")
	public String trainerSelect() {
	    return "gym/trainerSelect/trainerSelect";
	}
	
	
	
	
	/** 트레이너 값 불러오기~
	 * @param model
	 * @return
	 */
	@GetMapping("/trainer")
	public String getTrainer(
			Model model) {
		
		List<Trainer> trainer = service.getAllTrainers();
		
		model.addAttribute("trainer", trainer);
		
		return "gym/trainerSelect/trainerSelect";
	}
	
	
	
}
