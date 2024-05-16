package edu.kh.cooof.lesson.dashBoard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.member.model.dto.Member;

@Mapper
public interface DashBoardMapper {

	//회원이 듣는 강의 리스트 조회
	List<LessonListDTO> findLesson(int loginMemberId);

	//별점, 강의번호 넣기
	int insertReview(LessonListDTO lessonListDTO);

	//별점 등록시 이미 등록된 후기가 있는지 찾아보기
	int searchLessonNo(int lessonNo);

	//이미 등록된 후기(별점)가 있다면 update 실행
	int updateReview(LessonListDTO lessonListDTO);

	



}
