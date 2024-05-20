package edu.kh.cooof.lesson.list.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Lesson {
	
	// LESSONS 테이블 컬럼
	private int lessonNo;
	private String lessonTitle;
	private String lessonRoom;
	private String lessonSchedule;
	private String lessonStartDate;
	private String lessonEndDate;
	private int lessonCapacity;
	private int lessonTuitionFee;
	private int lessonMaterialFee;
	private String lessonTargetAudience;
	private int closeYn;
	private int memberNo;
	private String lessonRegisterStart;
	private String lessonRegisterEnd;
	private int lessonRemains;
	
	// 추후 추가 예정
	

}
