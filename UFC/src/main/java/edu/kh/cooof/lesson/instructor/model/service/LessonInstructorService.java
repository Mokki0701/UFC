package edu.kh.cooof.lesson.instructor.model.service;

import java.util.Map;

import edu.kh.cooof.lesson.instructor.model.dto.LessonInstructor;

public interface LessonInstructorService {

	/** 강사 목록 조회
	 * @return
	 */
	Map<String, Object> selectInstList();

	/** 강사 상세 조회
	 * @param instNo
	 * @return
	 */
	LessonInstructor selectDetailInst(int instNo);

	/** 강사 등록 요청
	 * @param memberNo
	 * @return
	 */
	int regRequest(int memberNo);

	/** 신청한적 있는지 확인(승낙 대기 상태인지 확인)
	 * @param memberNo
	 * @return
	 */
	Integer checkRequest(int memberNo);

	/** 강사 테이블에 존재하지 않기 때문에 추가
	 * @param map
	 * @return
	 */
	Integer addToInstTable(Map<String, Object> map);

}
