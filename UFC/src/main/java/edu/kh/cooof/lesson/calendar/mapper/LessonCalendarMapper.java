package edu.kh.cooof.lesson.calendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.MappedJdbcTypes;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

@Mapper
public interface LessonCalendarMapper {

	/** 달력 조회해오기
	 * @return
	 */
	List<Lesson> selectCalendar(int memberNo);
	
	
	
	

}
