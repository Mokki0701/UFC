package edu.kh.cooof.lib.seat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibSeatDTO {
	
	
	// 열람실 자리 현황
    private int seatNo;
    private int coordiX;
    private int coordiY;
    private int condition;
    private int seatAvail;
    
    
    // 열람실 이용하기
    private int rentSeatNo;
    private String readingStart;
    private String readingDone;
    private int readingExtend;
    private int seatNo2;
    private int memberNo;
}
