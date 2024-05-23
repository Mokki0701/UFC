package edu.kh.cooof.gym.gymReview.model.dto;

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

	private int gymNo;
	private String gymTitle;
	private String gymContent;
	private String gymUpdateDate;
	private String gymWriteDate;
	private int gymDelFl;
	private int memberNo;
	
}
