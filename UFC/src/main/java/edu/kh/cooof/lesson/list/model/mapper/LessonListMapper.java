package edu.kh.cooof.lesson.list.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.lesson.list.model.dto.Lesson;

@Mapper
public interface LessonListMapper {

	/** 모든 수업의 개수를 조회 (마감 여부 관계 없이)
	 * @return
	 */
	int getLessonCount();

	/** 지정된 페이지의 수업 목록 조회해오기
	 * @param rowBounds
	 * @return
	 */
	List<Lesson> selectLessonList(RowBounds rowBounds);

	/** 검색한 수업명과 겹치는 게시글의 개수를 조회
	 * @param paramMap
	 * @return
	 */
	int getSearchCount(Map<String, Object> paramMap);

	/** 검색한 페이지의 수업 목록 조회 해오기
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Lesson> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 선택한 수업 내용 상세 조회해오기
	 * @param lessonNo
	 * @return
	 */
	Lesson selectDetail(int lessonNo);

	/** 레슨 신청 (회원만 강사X, 관리자X)
	 * @param map
	 * @return 성공 여부
	 */
	int lessonSignup(Map<String, Integer> map);

	/** 현재 로그인한 회원이 해당 수업에 가입해 있는지 여부 확인
	 * @param map
	 * @return
	 */
	int signupCheck(Map<String, Integer> map);

	/** 레슨 신청 성공 시 잔여 -1 하기
	 * @param map
	 */
	void lessonCapacityDecrease(Map<String, Integer> map);

	/** 잔여 좌석 확인하기
	 * @param map
	 * @return
	 */
	int remainsCheck(Map<String, Integer> map);

	/** 즐겨찾기 여부 확인
	 * @param map
	 * @return
	 */
	int checkWishlist(Map<String, Integer> map);

	/** 기존에 등록된 즐겨찾기 삭제
	 * @param map
	 * @return
	 */
	int deleteWishlist(Map<String, Integer> map);

	/** 즐겨찾기 추가
	 * @param map
	 * @return
	 */
	int insertWishlist(Map<String, Integer> map);


}
