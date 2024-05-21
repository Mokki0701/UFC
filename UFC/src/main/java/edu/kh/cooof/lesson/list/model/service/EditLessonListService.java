package edu.kh.cooof.lesson.list.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

public interface EditLessonListService {

	/** 레슨 작성
	 * @param inputLesson
	 * @param inputImg
	 * @return
	 */
	int lessonInsert(Lesson inputLesson, MultipartFile inputImg) throws IllegalStateException, IOException;
	

	
	
}
