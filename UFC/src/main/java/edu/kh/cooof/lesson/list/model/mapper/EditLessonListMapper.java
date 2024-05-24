package edu.kh.cooof.lesson.list.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

@Mapper
public interface EditLessonListMapper {

	/** 레슨 insert
	 * @param inputLesson
	 * @return
	 */
	int lessonInsert(Lesson inputLesson);

	/** 레슨 update
	 * @param inputLesson
	 * @return
	 */
	int lessonUpdate(Lesson inputLesson);
	
	

}
