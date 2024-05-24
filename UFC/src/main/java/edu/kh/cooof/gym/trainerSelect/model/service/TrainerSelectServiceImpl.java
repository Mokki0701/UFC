package edu.kh.cooof.gym.trainerSelect.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.gym.trainerSelect.model.dto.PaymentRequest;
import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.mapper.TrainerSelectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerSelectServiceImpl implements TrainerSelectService {

	private final TrainerSelectMapper mapper;
	
	
	// 트레이너 전체 조회
	@Override
	public List<Trainer> getAllTrainers() {
		return mapper.getAllTrainers();
	}
	
	// 트레이너 번호 조회
	@Override
	public Trainer selectTrainer(int trainerNo) {
		return mapper.selectTrainer(trainerNo);
	}

	
	@Override
	public int gymPayment(PaymentRequest request) {
		return mapper.gymPayment(request);
	}
	
}
