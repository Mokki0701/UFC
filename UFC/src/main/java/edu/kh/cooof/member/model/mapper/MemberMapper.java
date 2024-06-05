package edu.kh.cooof.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.cooof.member.model.dto.Member;

// UFC 
@Mapper
public interface MemberMapper {

	Member loginMember(String memberEmail);

	// 회원가입 정보 넣기
	int signup(Member inputMember);

	// 로그인
	Member login(String memberEmail);


	// 아이디 중복 확인
	int checkEmail(String memberEmail);
	

	

    
}
