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
import edu.kh.cooof.lib.book.model.mapper.BookLoanMapper;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class LessonInstructorServiceImpl implements LessonInstructorService {

	private final LessonInstructorMapper mapper;
	
	private final BookLoanMapper messageMapper;

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

	// 강사 등록 요청
	@Override
	public int regRequest(int memberNo) {

		return mapper.regRequest(memberNo);
	}

	// 신청한적 있는지 확인(승낙 대기 상태인지 확인)
	@Override
	public Integer checkRequest(int memberNo) {
		return mapper.checkRequest(memberNo);
	}

	// 강사 테이블에 존재하지 않기 때문에 추가
	@Override
	public Integer addToInstTable(Map<String, Object> map) {
		return mapper.addToInstTable(map);
	}

	// 강사 승인 대기중인 인원들 조회
	@Override
	public List<Member> instRegCheck() {
		return mapper.instRegCheck();
	}

	// 강사 승인
	@Override
	public int instAccept(int memberNo, int loginMemberNo) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("applyMemberNo", memberNo);
		map.put("memberNo", loginMemberNo);
		map.put("checkNo", 3);
		
		messageMapper.transmitMessage(map);
		
		return mapper.instAccept(memberNo);
	}
	
	// 강사 거절
	@Override
	public int instReject(int memberNo, int loginMemberNo) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("applyMemberNo", memberNo);
		map.put("memberNo", loginMemberNo);
		map.put("checkNo", 4);
		
		messageMapper.transmitMessage(map);
		
		return mapper.instReject(memberNo);
	}

	
	
	
	
}
