package edu.kh.cooof.gym.gymReview.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymReview {

	private int gymNo; // 테이블 번호
	private String gymTitle; // 
	private String gymContent;
	private Date gymUpdateDate;
	private Date gymWriteDate;
	private int gymDelFl;
	private int memberNo;
	private int viewCount;
	private String memberLastName; 
	private String memberFirstName;
	
	
	
}
