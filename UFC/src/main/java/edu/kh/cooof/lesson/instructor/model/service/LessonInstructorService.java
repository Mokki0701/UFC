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

}
