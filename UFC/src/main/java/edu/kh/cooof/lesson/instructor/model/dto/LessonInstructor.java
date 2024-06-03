package edu.kh.cooof.lesson.instructor.model.dto;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 강사 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LessonInstructor {
	
	// LESSON_INSTRUCTOR 테이블 칼럼명들
	private int memberNo;
	private int instStat;
	private String instCategory;
	private String instIntro;
	
	
	// MEMBER 테이블 조인 후 사용할 값들
	private String memberEmail;
	private String memberPw;
	private String memberLastName; 
	private String memberFirstName;
	private String memberBirthday;
	private String memberPhone;
	private int memberAuthority;
	private String memberGender;
	
	// 추후 추가 할지도?
	
	

}
