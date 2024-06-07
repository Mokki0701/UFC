package edu.kh.cooof.common.scheduler.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;
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
	public LibSeatDTO checkReadingDone(Date sysdate) {
		
		return  mapper.checkReadingDone(sysdate);
	}
	
	// 열람실 이용 종료 실행
	@Override
	public int finishUsingSeat(Map<String, Object> expiredSeat) {
		
		// 빌린 기록 지우기
		int setSeat = mapper.finishUsingSeat(expiredSeat);
		
		// 좌석 상태 업데이트하기
		int setAvail = mapper.setAvail(expiredSeat);
		
		return 0;
	}
}
