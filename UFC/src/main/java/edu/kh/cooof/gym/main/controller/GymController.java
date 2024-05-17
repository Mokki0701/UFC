package edu.kh.cooof.gym.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.gym.main.model.service.GymService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("gym")
public class GymController  {
	
	private final GymService service;
	
	@GetMapping("gymMain")
	public String gymPage() {
	    return "gym/gymMain";
	}
	
	
	


	
	
	
}
