package edu.kh.cooof.gym.trainerSelect.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {

	private int trainerNo;
	private String trainerName;
	private int trainerPrice;
	private String trainerImg;
	private int memberNo;
	private int price;
	
	
	// member 전화번호
	private String memberPhone;
	
}


