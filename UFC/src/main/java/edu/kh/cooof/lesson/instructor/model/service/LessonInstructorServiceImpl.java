package edu.kh.cooof.lesson.instructor.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.common.LessonUtil;
import edu.kh.cooof.lesson.instructor.model.dto.LessonInstructor;
import edu.kh.cooof.lesson.instructor.model.mapper.LessonInstructorMapper;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.mapper.EditLessonListMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class LessonInstructorServiceImpl implements LessonInstructorService {
	
	private final LessonInstructorMapper mapper;
	
	// 강사 조회
	@Override
	public Map<String, Object> selectInstList() {
		List<LessonInstructor> instList = mapper.selectInstList();

		// 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();

		map.put("instList", instList);

		// 결과 반환
		return map;
	}
	
	
	@Override
	public LessonInstructor selectDetailInst(int instNo) {
		
		return mapper.selectDetailInst(instNo);
	}
	
	
	
	
	
	
	

}
