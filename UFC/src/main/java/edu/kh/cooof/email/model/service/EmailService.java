package edu.kh.cooof.email.model.service;

import java.util.Map;

public interface EmailService {

	//이메일 보내기
	String sendEmail(String string, String email);

	// 이메일 , 인증번호확
	int checkAuthKey(Map<String, Object> map);

}
