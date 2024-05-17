package edu.kh.cooof.gym.information.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.information.model.dto.UseMachine;
import edu.kh.cooof.gym.information.model.mapper.UseMachineMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UseMachineServiceImpl implements UseMachineService{
	
	private final UseMachineMapper mapper; 
	
	@Override
	public List<UseMachine> getAllUseMachines() {
		
		return mapper.getAllUseMachines();
	}
	
	@Override
	public UseMachine getUseMachineById(int id) {
		
		return mapper.getUseMachineById(id);
	}
	
	
}
