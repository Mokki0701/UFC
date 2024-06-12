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
	private int deleteYn;
	
	// 데이터 가공해서 써야되는 것들
	private String lessonScheduleDay; // 수업 요일만 나옴
	private String lessonScheduleTime; // 수업 시간만 나옴
	
	// 즐겨찾기 관련 컬럼 (LESSONS_WISHLIST)
	private int wishListYN; // 0 : 즐찾 X, 1 : 즐찾 O
	
	// MEMBER 테이블 JOIN 후 사용할 것
	private String instName;
	
	// 달력에서 쓸 필드
	private String wishYn; // 즐찾 Y 안되있음 N
	private String enrollYn; // 수업 등록 되있으면 Y 아니면 N
	
	
	
	
	

}
