package edu.kh.cooof.common.scheduler.service;

import java.util.List;

public interface CommonSchedulingService {

	/** 삭제 처리된 수업 이미지 파일명 조회
	 * @return
	 */
	List<String> selectLessonImageNames();

}
