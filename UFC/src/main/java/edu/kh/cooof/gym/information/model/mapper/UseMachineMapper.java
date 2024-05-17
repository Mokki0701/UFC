package edu.kh.cooof.gym.information.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.gym.information.model.dto.UseMachine;

@Mapper
public interface UseMachineMapper {

    List<UseMachine> getAllUseMachines();

	UseMachine getUseMachineById(int id);

	

}
