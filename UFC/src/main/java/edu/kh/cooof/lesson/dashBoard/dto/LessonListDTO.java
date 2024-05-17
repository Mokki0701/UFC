package edu.kh.cooof.lesson.dashBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonListDTO {
	
	private String lessonTitle;
	private int lessonNo;
	private int lessonStar;
	private int memberNo;

}
