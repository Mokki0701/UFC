package edu.kh.project.main.model.service;

public interface MainService {

	/** 비밀번호 초기화
	 * @param inputNo
	 * @return result
	 */
	int resetPw(int inputNo);

	/** 탈퇴 복구
	 * @param inputNo
	 * @return result
	 */
	int restoration(int inputNo);

}
