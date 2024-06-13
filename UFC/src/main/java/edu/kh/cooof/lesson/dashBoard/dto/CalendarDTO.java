package edu.kh.cooof.lesson.dashBoard.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalendarDTO {

	private String title;
    private String start;
    private String end;
	
	
}
