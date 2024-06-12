package edu.kh.cooof.lib.space.model.dto;

import java.util.Map;

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
public class SpaceRentInfoDTO {

	// 공간의 이용 정보 불러오는  dto
	
	private int repeat;
    private Map<String, Object> spaceDoneTime;


	
	
}
