package edu.kh.cooof.lesson.dashBoard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.dashBoard.dto.AttendanceDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonInstructorDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.member.model.dto.Member;

@Mapper
public interface DashBoardMapper {

	//회원이 듣는 강의 리스트 조회
	List<LessonListDTO> findLesson(int loginMemberId);

	//별점, 강의번호 넣기
	int insertReview(LessonListDTO lessonListDTO);

	//별점 등록시 이미 등록된 후기가 있는지 찾아보기
	int searchLessonNo(LessonListDTO lessonListDTO);

	//이미 등록된 후기(별점)가 있다면 update 실행
	int updateReview(LessonListDTO lessonListDTO);

	//내가 등록한 강의의 별점 찾기
	int findStar(LessonListDTO lessonList);

	//강사 강의 찾기
	List<LessonInstructorDTO> instructorLesson(int loginMemberId);
	

	//출석부 리스트
	List<LessonListDTO> confirmLesson(LessonListDTO studentList);

	// 해당 날짜의 강의가 맞는지 확인
	int countLesson(LessonListDTO studentList);

	// 출석 등록
	int addList(List<AttendanceDTO> attendanceList);

	//강사 별점 리뷰 확인
	int checkReview(int lessonNo);

	//출석현황
	List<AttendanceDTO> statusCheck(AttendanceDTO attendanceStatus);

	//전체 수업일자
	int getTotalLessonDays(int lessonNo);

	//특정 회원의 특정 강의에 대한 출석 일수 
	int getAttendanceCount(AttendanceDTO attendanceDTO);

	//중복삭제
	int deleteList(List<AttendanceDTO> attendanceList);


	
	



}
