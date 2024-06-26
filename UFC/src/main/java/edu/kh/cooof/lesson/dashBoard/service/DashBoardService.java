package edu.kh.cooof.lesson.dashBoard.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lesson.dashBoard.dto.AttendanceDTO;
import edu.kh.cooof.lesson.dashBoard.dto.CalendarDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonInstructorDTO;
import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.member.model.dto.Member;

public interface DashBoardService {

	//회원이 듣는 강의 리스트 조회
	List<LessonListDTO> findLesson(int loginMemberId);

	//별점, 강의번호 넣기
	int insertReview(LessonListDTO lessonListDTO);

	//내가 등록한 강의의 별점 찾기
	int findStar(LessonListDTO lessonList);

	//강사 강의 찾기
	List<LessonInstructorDTO> instructorLesson(int loginMemberId);

	// 출석 리스트 조회
	List<LessonListDTO> confirmLesson(LessonListDTO studentList);

	//출석 등록 
	int addList(List<AttendanceDTO> attendanceList);

	//강사 별점 리뷰 확인
	int checkReview(int lessonNo);

	// 출석현황
	List<AttendanceDTO> statusCheck(AttendanceDTO attendanceStatus);

	// 출석률
	List<Map<String, Object>> getAttendanceRates(int memberNo);

	// 중복 삭제
	int deleteList(List<AttendanceDTO> attendanceList);

	//즐겨찾기
	List<LessonListDTO> bookmarkList(int loginMemberId);

	//즐겨찾기 삭제
	int bookmarkRemove(LessonListDTO lessonList);

	/** 수료한 수업 목록 조회
	 * @param loginMemberId
	 * @return
	 */
	List<LessonListDTO> finishedList(int loginMemberId);

	/** 출석률이 100%인 수업만 반환하는 메소드
	 * @param memberNo
	 * @return
	 */
	List<Map<String, Object>> getPerfectAttendanceLessons(int memberNo);

	/** 수료증 신청한 수업 조회
	 * @param lessonNo
	 * @return
	 */
	Lesson selectCertificateLesson(int lessonNo);

	/** 수료증 수료할 회원 정보 가공을 위한 조회
	 * @param memberNo
	 * @return
	 */
	Member selectCertificateMember(int memberNo);
	
	

	

	



	
	

}
