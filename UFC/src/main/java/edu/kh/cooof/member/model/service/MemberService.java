package edu.kh.cooof.member.model.service;

import edu.kh.cooof.member.model.dto.Member;

public interface MemberService {

	// 빠른 로그인
	Member quickLogin(String memberEmail);


}
