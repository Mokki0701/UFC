package edu.kh.cooof.common.scheduler.service;

import java.util.List;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

public interface CommonSchedulingService {

	/** 삭제 처리된 수업 이미지 파일명 조회
	 * @return
	 */
	List<String> selectLessonImageNames();

	/** 잔여좌석이 없는 수업 조회
	 * @return
	 */
	List<Lesson> checkRemains();

}
