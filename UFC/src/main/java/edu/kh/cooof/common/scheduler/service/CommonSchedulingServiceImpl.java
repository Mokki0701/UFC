package edu.kh.cooof.common.scheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonSchedulingServiceImpl implements CommonSchedulingService {
	
	private final SchedulingMapper mapper;
	
	// 삭제된 수업들 이미지 조회
	@Override
	public List<String> selectLessonImageNames() {
		return mapper.selectLessonImageNames();
	}

	// 잔여 좌석이 없는 수업 조회
	@Override
	public List<Lesson> checkRemains() {
		return mapper.checkRemains();
	}
	
	@Override
	public void setCloseYn(int lessonNo) {
		mapper.setCloseYn(lessonNo);		
	}
	
}