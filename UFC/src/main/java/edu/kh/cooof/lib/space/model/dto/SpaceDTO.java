package edu.kh.cooof.lib.space.model.dto;

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
public class SpaceDTO {

	private String spaceName; 
	private int spaceAvail;
	
	// 생성된 공간의 넘버, 위치, 크기
	private int spaceNo;    
	private int left;
	private int top;
	private int width;
	private int height;
	
	private int memberNo;
	
	private int spaceExtend; // 공간의 남은 연장 기회
	private String startTime; // 예약을 시작하고자 하는 시간

	private String endTime; // 예약 종료 시간
	
	private String startBooking; // 예약이된 시작 시간
	

	private String spaceStart;
	private String spaceDone;
	private int spaceNo2;
<<<<<<< HEAD
	
=======
>>>>>>> 2f91a2498bb1a94396473872029b3272de6ae1c3

	
	
}
