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
	private String memberBirthday;
	private String memberPhone;
	
	// ... 
	private int memberAuthority;
	
	// 이후에 추가..
}
