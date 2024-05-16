package edu.kh.cooof.lesson.list.model.service;

import java.util.Map;

public interface LessonListService {

	/** 레슨 리스트 조회 (검색X)
	 * @param cp : 페이지네이션용
	 * @return map : 조회 결과 반환용
	 */
	Map<String, Object> selectLessonList(int cp);

}
