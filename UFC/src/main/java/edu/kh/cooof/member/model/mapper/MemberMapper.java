package edu.kh.cooof.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.cooof.member.model.dto.Member;

// UFC 
@Mapper
public interface MemberMapper {

	Member loginMember(String memberEmail);

	// 이메일로 회원을 조회하는 메서드
	Member findEmail(@Param("email") String email);
	
    // 회원 ID로 회원을 조회하는 메서드
    Member findById(int memberNo);
	
    // 회원 권한을 업데이트하는 메서드
    int updateMemberAuthority(int memberNo, int memberAuthority);
    
}
