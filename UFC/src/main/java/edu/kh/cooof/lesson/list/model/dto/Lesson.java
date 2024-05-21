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
	private String imgPath;
	private String lessonDetail;
	
	// 데이터 가공해서 써야되는 것들
	private String lessonScheduleDay; // 요일만 나옴
	

}
