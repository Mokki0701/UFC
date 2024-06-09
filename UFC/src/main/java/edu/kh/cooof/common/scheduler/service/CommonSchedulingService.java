package edu.kh.cooof.common.scheduler.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;

public interface CommonSchedulingService {

	/**
	 * 삭제 처리된 수업 이미지 파일명 조회
	 * 
	 * @return
	 */
	List<String> selectLessonImageNames();

	/**
	 * 잔여좌석이 없는 수업 조회
	 * 
	 * @return
	 */
	List<Lesson> checkRemains();

	/**
	 * 잔여 정원 0인 수업 삭제 처리
	 * 
	 * @param lessonNo
	 */
	void setCloseYn(int lessonNo);

	/**
	 * 권한 부여 체크
	 * 
	 * @return
	 */
	int authorityCheck();

	/**
	 * 도서 대출 예약 희망 전체 삭제
	 * 
	 */
	void deleteLibAll();

	// 열람실 이용 종료 시간 체크하기
	List<LibSeatDTO> checkReadingDone(Date sysdate);
	
	// 열람실 이용 종료 실행
	int finishUsingSeat(Map<String, Object> expiredSeat);

}
