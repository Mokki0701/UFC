package edu.kh.cooof.member.model.dto;

import groovy.transform.ToString;
import java.sql.Date;

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
@ToString
public class Member {

	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberLastName; 
	private String memberFirstName;
	private String memberBirthday;
	private String memberPhone;
	private int memberCancel;
	// ... 
	private int memberAuthority;
	private String memberGender;
	// 주소
	private String memberAddress;
	
	// 현재 이용 중인 좌석 번호
	private int curUsingSeatNo;
	
	// 강사 테이블 JOIN 후 사용 
	private int instStat; // 강사 상태
	private String instCategory; // 강의 분야
	private String instIntro; // 강사 소개
	private String instResume; // 제출한 이력서 경로
	private String instInfo; // 생성된 회원 정보 PDF 경로
	
	private int blockBeing;
	
	private String Postcode;
	private String RoadAddress;
	private String DetailAddress;
	
	
	
	
	
	// 이후에 추가..
}
