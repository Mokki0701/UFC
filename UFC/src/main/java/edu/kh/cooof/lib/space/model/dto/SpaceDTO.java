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
	
	private String startTime; // 예약을 시작하고자 하는 시간
	private String spaceStart;
	private String spaceDone;
	private int spaceNo2;
	private int spaceExtend;
	
	
}
