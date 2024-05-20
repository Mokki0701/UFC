package edu.kh.cooof.lesson.dashBoard.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.dashBoard.dto.LessonInstructorDTO;
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
	
	//별점, 강의번호 넣기
	@Override
	public int insertReview(LessonListDTO lessonListDTO) {
		
		
		//별점 등록시 이미 등록된 후기가 있는지 찾아보기
		int lessonNo = mapper.searchLessonNo(lessonListDTO);
		
		int result = 0;
		
		if(lessonNo > 0) {//이미 등록된 후기가 있음
			
			//update 실행
			result = mapper.updateReview(lessonListDTO);
		}else if(lessonNo == 0) {//등록된 후기 없음
			//insert 실행
			result =mapper.insertReview(lessonListDTO);
		}
		
		//별점 삽입 실패 시
		if(result == 0) return 0;
		
		return result;
	}
	
	
	//내가 등록한 강의의 별점 찾기
	@Override
	public int findStar(LessonListDTO lessonList) {
		return mapper.findStar(lessonList);
	}
	
	
	//강사 강의 찾기
	@Override
	public List<LessonInstructorDTO> instructorLesson(int loginMemberId) {
		return mapper.instructorLesson(loginMemberId);
	}
	
	//학생 출석부 조회
	@Override
	public List<LessonListDTO> AttendanceList(LessonInstructorDTO studentList) {
		return mapper.attendanceList(studentList);
	}
	
}
