package edu.kh.cooof.lesson.dashBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
	
	private int lessonNo;
	private String date;
	private int memberNo; // 학생번호
	
	private String attendanceStatus; // N or Y
	
}
