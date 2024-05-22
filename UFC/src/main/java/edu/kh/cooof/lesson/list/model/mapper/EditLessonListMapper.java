package edu.kh.cooof.lesson.list.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

@Mapper
public interface EditLessonListMapper {

	/** 레슨 삽입
	 * @param inputLesson
	 * @return
	 */
	int lessonInsert(Lesson inputLesson);
	
	

}
