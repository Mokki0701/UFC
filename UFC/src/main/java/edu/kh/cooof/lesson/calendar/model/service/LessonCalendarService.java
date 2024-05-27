package edu.kh.cooof.lesson.calendar.model.service;

import java.util.List;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

public interface LessonCalendarService {

	/** 달력 조회 해오기 (수업들)
	 * @param i 
	 * @return
	 */
	List<Lesson> selectCalendar(int memberNo);

}
