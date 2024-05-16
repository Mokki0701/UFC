package edu.kh.cooof.lesson.dashBoard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.member.model.dto.Member;

@Mapper
public interface DashBoardMapper {

	//회원이 듣는 강의 리스트 조회
	List<LessonListDTO> findLesson(int loginMemberId);



}
