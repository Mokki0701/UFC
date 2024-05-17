package edu.kh.cooof.gym.information.model.service;

import java.util.List;
import edu.kh.cooof.gym.information.model.dto.UseMachine;

public interface UseMachineService {

	List<UseMachine> getAllUseMachines();

	UseMachine getUseMachineById(int id);
	
	
}
