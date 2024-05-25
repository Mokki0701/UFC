package edu.kh.cooof.lesson.calendar.model.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.calendar.mapper.LessonCalendarMapper;
import edu.kh.cooof.lesson.common.LessonUtil;
import edu.kh.cooof.lesson.list.model.mapper.EditLessonListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
@Transactional
public class LessonCalendarServiceImpl implements LessonCalendarService{
	
	public final LessonCalendarMapper mapper;
	
	
	

}
