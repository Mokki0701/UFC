package edu.kh.cooof.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberLastName; 
	private String memberFirstName;
	private String memberPhone;
	// ... 
	private int memberAuthority;
	
	// 현재 이용 중인 좌석 번호
	private int curUsingSeatNo;
	
	
	// 이후에 추가..
}
