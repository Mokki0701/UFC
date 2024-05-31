package edu.kh.cooof.lesson.instructor.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 강사 DTO


@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonInstructor {
	
	// LESSON_INSTRUCTOR 테이블 칼럼명들
	private int memberNo;
	private int instStat;
	private String instCategory;
	private String instIntro;
	
	// 추후 추가 예정
	
	

}
