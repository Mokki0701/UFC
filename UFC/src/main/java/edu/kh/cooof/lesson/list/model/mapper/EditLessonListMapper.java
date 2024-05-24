package edu.kh.cooof.lesson.list.model.mapper;

import java.util.Map;

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

	/** 레슨 삭제
	 * @param lessonNo
	 * @return
	 */
	int lessonDelete(int lessonNo);

	/** 입력받은 요일로 요일 소분류 코드 받아오기
	 * @param string
	 * @return
	 */
	int getSmallCodeBySmallName(String string);

	/** 태그 삽입
	 * @param map
	 */
	void insertLessonCategory(Map<String, Integer> map);

	/** 기존 태그 전부 삭제
	 * @param lessonNo
	 */
	void deleteTags(Integer lessonNo);
	
	

}
