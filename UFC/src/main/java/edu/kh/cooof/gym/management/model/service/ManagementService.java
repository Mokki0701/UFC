package edu.kh.cooof.gym.management.model.service;

import java.util.List;

import edu.kh.cooof.gym.application.model.dto.Application;

public interface ManagementService {

	List<Application> selectApplications();

	Application getApplicationNo(int applicationNo);

	boolean sendEmail(String email, String string);

	int updateMemberAuthority(Integer memberNo);

	int updateApplicationStatus(int memberNo, String status);

	int addTrainer(Application app);


}
