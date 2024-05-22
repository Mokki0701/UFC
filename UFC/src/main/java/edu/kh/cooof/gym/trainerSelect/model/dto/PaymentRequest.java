package edu.kh.cooof.gym.trainerSelect.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

	private int ptCount;
    private int ptPrice;
    private String strDate;
    private String ptLkroom;
    private int memberNo;
    private int trainerNo;
}
