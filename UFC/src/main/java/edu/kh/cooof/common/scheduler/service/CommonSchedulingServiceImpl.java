package edu.kh.cooof.common.scheduler.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
	public int setCloseYn(int lessonNo) {
		return mapper.setCloseYn(lessonNo);		
	}
	
	// 권한 부여 체크
	@Override
	public int authorityCheck() {
		return mapper.authorityCheck();
	}
	
	@Override
	public void deleteLibAll() {
		mapper.deleteLibLoan();
		mapper.deleteLibRent();
		mapper.deleteLibHope();
	}
	
	// 열람실 이용 종료 시간 체크하기
	@Override
	public List<LibSeatDTO> checkReadingDone(Date sysdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.printf("Checking reading done for time: %s%n", sdf.format(sysdate));
		return mapper.checkReadingDone(sysdate);
	}
	
	// 열람실 이용 종료 실행
	@Override
	@Transactional
	public int finishUsingSeat(Map<String, Object> expiredSeat) {
		// 빌린 기록 지우기
		int setSeat = mapper.finishUsingSeat(expiredSeat);

		// 좌석 상태 업데이트하기
		int setAvail = mapper.setAvail(expiredSeat);

		// 두 작업의 성공 여부를 합산하여 반환
		return setSeat + setAvail;
	}
	
	// 마감 처리된 수업 마감 태그 추가
	@Override
	public int setCloseTagAdd(int lessonNo) {
		return mapper.setCloseTagAdd(lessonNo);
	}
	
	@Override
	public int removeOpenTag(int lessonNo) {
		return mapper.removeOpenTag(lessonNo);
	}
	
	@Override
	public void deleteMemberAll() {
		
		List<Integer> memberNoList = mapper.selectMemberList();
		for(int memberNo : memberNoList) {
			mapper.deleteMember(memberNo);
		}
		
	}
	
}
