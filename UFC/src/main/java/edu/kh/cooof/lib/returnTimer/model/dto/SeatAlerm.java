package edu.kh.cooof.lib.returnTimer.model.dto;

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
public class SeatAlerm {
	
	private int seatUserNo;
	private int checkTime; // 0이면 5분전, 1이면 종료 시간
	
}
