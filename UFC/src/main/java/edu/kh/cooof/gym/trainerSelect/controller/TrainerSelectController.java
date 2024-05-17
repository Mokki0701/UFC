package edu.kh.cooof.gym.trainerSelect.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.service.TrainerSelectService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainerSelect")

public class TrainerSelectController {

	private final TrainerSelectService service;
	
	@GetMapping("/trainerSelect")
	public String trainerSelect(
			Model model) {
		
		List<Trainer> trainers = service.getAllTrainers();	
		model.addAttribute("trainers", trainers);
	    return "gym/trainerSelect/trainerSelect";
	}
	
	@GetMapping("trainerPrice")
	public String trainerPrice(
			Model model){
		List<Trainer> trainers = service.getAllTrainers();
	    model.addAttribute("trainers", trainers);
		return "gym/trainerSelect/trainerPrice";
	}
	
    
	
	
	
	
	
	
}
