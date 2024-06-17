package edu.kh.cooof.common.scheduler.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;

@Mapper
public interface SchedulingMapper {
	
	/** 삭제 처리된 수업들의 이미지 조회
	 * @return
	 */
	List<String> selectLessonImageNames();

	/** 잔여좌석이 없는 수업 조회
	 * @return
	 */
	List<Lesson> checkRemains();

	/** 잔여좌석이 없는 수업 마감 처리
	 * @param lessonNo
	 * @return 
	 */
	int setCloseYn(int lessonNo);

	/** 강사 권한 부여 체크
	 * @return
	 */
	int authorityCheck();
	
	void deleteLibLoan();

	void deleteLibRent();

	void deleteLibHope();

	// 열람실 이용 종료 5분 전인 사람의 memberNo select
	List<Integer> getFiveMinBeforeMemberNo(Date fiveMinBefore);
	
	
	// 열람실 이용 종료 시간 체크하기
	List<LibSeatDTO> checkReadingDone(Date sysdate);
	 
	// 열람실 이용 종료 실행하기 : 해당 사용자 사용 종료시키기
	int finishUsingSeat(Map<String, Object> expiredSeat);

	// 열람실 이용 종료 실행하기 : 해당 좌석 상태 업데이트 하기
	int setAvail(Map<String, Object> expiredSeat);

	/** 마감 처리된 수업 마감 태그 추가
	 * @param lessonNo
	 * @return
	 */
	int setCloseTagAdd(int lessonNo);

	/** 모집중 태그 삭제
	 * @param lessonNo
	 * @return
	 */
	int removeOpenTag(int lessonNo);

	List<Integer> selectMemberList();

	void deleteMember(int memberNo);


	
	

}
