package edu.kh.cooof.lesson.instructor.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.instructor.model.dto.LessonInstructor;

@Mapper
public interface LessonInstructorMapper {

	/** 강사 목록 조회
	 * @return
	 */
	List<LessonInstructor> selectInstList();

	/** 강사 상세 조회
	 * @param instNo
	 * @return
	 */
	LessonInstructor selectDetailInst(int instNo);
	
	

}
