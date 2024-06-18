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
public class LessonListDTO {
	
	private String lessonTitle;
	private int lessonNo;
	private int lessonStar;
	private int memberNo;
	private String date;
	private String fullName;
	//즐겨찾기
	private String lessonSchedule; 
	private int deleteYn;

}
