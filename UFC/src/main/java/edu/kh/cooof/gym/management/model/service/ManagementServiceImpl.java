package edu.kh.cooof.gym.management.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.management.model.mapper.ManagementMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{
	
	private final ManagementMapper mapper;
	
	@Override
	public List<Application> selectApplications() {

		return mapper.selectApplications();
	}
	
	@Override
	public Application getApplicationNo(int applicationNo) {

		return mapper.selectApplicationNo(applicationNo);
	}
	
}
