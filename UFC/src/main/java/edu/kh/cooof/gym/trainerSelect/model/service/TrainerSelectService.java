package edu.kh.cooof.gym.trainerSelect.model.service;

import java.util.List;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;

public interface TrainerSelectService {

	/** 트레이너 값 불러오기
	 * @return
	 */
	List<Trainer> getAllTrainers();

	
}
