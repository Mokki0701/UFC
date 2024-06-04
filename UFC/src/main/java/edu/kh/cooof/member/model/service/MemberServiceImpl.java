package edu.kh.cooof.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final BCryptPasswordEncoder bcrypt;
	
	
	// 빠른 로그인
	@Override
	public Member quickLogin(String memberEmail) {
		
		Member loginMember = mapper.loginMember(memberEmail);
		
		if(loginMember == null) return null;
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
	// 로그인
	@Override
	public Member login(Member inputMember) {
		
	
		
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		if(loginMember == null) return null;
		
		// 일치하지 않을 때
		if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		loginMember.setMemberPw(null);
		
		return loginMember;
	}
	
	
	// 회원가입 
	@Override
	public int memberSignup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력된 경우
		if(!inputMember.getMemberAddress().equals(",,")) {
			
			// 주소 구분자 ^^^
			String address = String.join("^^^", memberAddress);
			
			
			inputMember.setMemberAddress(address);
		} else { // 입력되지 않은 경우
			inputMember.setMemberAddress(null);
		}
		
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		return mapper.signup(inputMember);
	}
	
	
	@Override
	public String checkEmail(String email) {
		
		return mapper.checkEmail(email);
	}

	
	
}
