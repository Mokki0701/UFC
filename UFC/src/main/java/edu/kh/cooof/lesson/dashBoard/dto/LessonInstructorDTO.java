package edu.kh.cooof.lesson.dashBoard.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
public class LessonInstructorDTO {
	
	private String lessonTitle;
	private int lessonNo;
	private int memberNo;
	
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	private LocalDate date;
	private String date;

}
