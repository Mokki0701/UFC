package edu.kh.cooof.lesson.list.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

public interface EditLessonListService {

	/** 레슨 작성
	 * @param inputLesson
	 * @param inputImg
	 * @return lessonNo
	 */
	int lessonInsert(Lesson inputLesson, MultipartFile inputImg) throws IllegalStateException, IOException;

	/** 레슨 수정
	 * @param inputLesson
	 * @param inputImg
	 * @return result
	 */
	int lessonUpdate(Lesson inputLesson, MultipartFile inputImg) throws IllegalStateException, IOException;

	/** 수업 삭제 후 결과 반환
	 * @param lessonNo
	 * @return 결과
	 */
	int lessonDelete(int lessonNo);

}
