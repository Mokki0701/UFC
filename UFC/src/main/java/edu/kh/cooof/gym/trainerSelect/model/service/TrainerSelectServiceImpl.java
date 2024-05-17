package edu.kh.cooof.gym.trainerSelect.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.mapper.TrainerSelectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainerSelectServiceImpl implements TrainerSelectService {

	private final TrainerSelectMapper mapper;
	
	
	@Override
	public List<Trainer> getAllTrainers() {
		return mapper.getAllTrainers();
	}
}
