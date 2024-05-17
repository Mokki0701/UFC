package edu.kh.cooof.gym.main.model.service;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.main.model.mapper.GymMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GymServiceImpl implements GymService {
	
	private final GymMapper mapper;
}
