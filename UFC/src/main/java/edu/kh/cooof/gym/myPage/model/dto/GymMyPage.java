package edu.kh.cooof.gym.myPage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GymMyPage {
	
    private String memberLastName;
    private String memberFirstName;
	private int ptYN;
	private int ptCount;
	private int ptPrice;
	private int ptLkroom;
	private String trainerName;
	private Date ptStrDate;
	
	
	
	
	
}
