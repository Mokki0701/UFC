package edu.kh.cooof.common.scheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonSchedulingServiceImpl implements CommonSchedulingService {
	
	private final SchedulingMapper mapper;
	
	@Override
	public List<String> selectLessonImageNames() {
		return mapper.selectLessonImageNames();
	}

}
