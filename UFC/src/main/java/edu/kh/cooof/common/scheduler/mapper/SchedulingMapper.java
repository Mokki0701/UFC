package edu.kh.cooof.common.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
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
	 */
	void setCloseYn(int lessonNo);

	/** 강사 권한 부여 체크
	 * @return
	 */
	List<Member> authorityCheck();

}
