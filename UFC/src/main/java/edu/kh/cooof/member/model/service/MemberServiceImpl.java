package edu.kh.cooof.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	
	// 빠른 로그인
	@Override
	public Member quickLogin(String memberEmail) {
		
		Member loginMember = mapper.loginMember(memberEmail);
		
		if(loginMember == null) return null;
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
	
	
}
