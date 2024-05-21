package edu.kh.cooof.member.model.service;

import edu.kh.cooof.member.model.dto.Member;

public interface MemberService {

	// 빠른 로그인
	Member quickLogin(String memberEmail);

	// 이메일 찾기
	Member findEmail(String email);

	Member findById(int memberNo);

	int updateMemberAuthority(int memberNo, int i);


}
