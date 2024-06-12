package edu.kh.cooof.member.model.service;

import edu.kh.cooof.member.model.dto.Member;

public interface MemberService {

	// 빠른 로그인
	Member quickLogin(String memberEmail);

	// 회원가입 정보 넣기
	int memberSignup(Member inputMember, String[] memberAddress);

	// 로그인 하기
	Member login(Member inputMember);

	// 아이디 중복 확인
	int checkEmail(String memberEmail);


	// 비밀번호 변경
	int changePw(String currentPw, String newPw, Member loginMember);

	// 주소 생일 전화번호 변경
	int changeAnything(Member inputMember, String[] memberAddress);
	


	


	


}
