package edu.kh.cooof.gym.application.model.service;

import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

	// 지원서 제출
	int submitApplication(String position, String applyRoute, MultipartFile resume);

	// 지원서 승인
	int acceptApplication(int applicationNo);

	// 지원서 거절
	int rejectApplication(int applicationNo);

}
