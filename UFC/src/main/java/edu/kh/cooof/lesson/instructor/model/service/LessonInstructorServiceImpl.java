package edu.kh.cooof.lesson.instructor.model.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.common.LessonUtil;
import edu.kh.cooof.lesson.instructor.model.mapper.LessonInstructorMapper;
import edu.kh.cooof.lesson.list.model.mapper.EditLessonListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class LessonInstructorServiceImpl implements LessonInstructorService {
	
	private final LessonInstructorMapper mapper;
	
	
	
	
	
	
	
	
	
	
	

}
