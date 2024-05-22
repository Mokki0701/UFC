package edu.kh.cooof.lesson.list.model.service;

import java.util.Map;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

public interface LessonListService {

	/** 레슨 리스트 조회 (검색X)
	 * @param cp : 페이지네이션용
	 * @return map : 조회 결과 반환용
	 */
	Map<String, Object> selectLessonList(int cp);

	/** 레슨 리스트 조회 (검색O)
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);

	/** 레슨 상세 조회
	 * @param lessonNo
	 * @return
	 */
	Lesson selectDetail(int lessonNo);

	/** 레슨 신청 (회원만 강사X, 관리자X)
	 * @param map
	 * @return 성공 여부
	 */
	int lessonSignup(Map<String, Integer> map);

}
