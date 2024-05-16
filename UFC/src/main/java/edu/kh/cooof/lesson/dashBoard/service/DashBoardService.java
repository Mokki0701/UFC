package edu.kh.cooof.lesson.dashBoard.service;

import java.util.List;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.member.model.dto.Member;

public interface DashBoardService {

	//회원이 듣는 강의 리스트 조회
	List<LessonListDTO> findLesson(int loginMemberId);

	
	

}
