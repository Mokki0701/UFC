package edu.kh.cooof.lesson.dashBoard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lesson.dashBoard.mapper.DashBoardMapper;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

	private final DashBoardMapper mapper;
	
	//회원이 듣는 강의 리스트 조회
	@Override
	public List<LessonListDTO> findLesson(int loginMemberId) {
		return mapper.findLesson(loginMemberId);
	}
	
	
}
