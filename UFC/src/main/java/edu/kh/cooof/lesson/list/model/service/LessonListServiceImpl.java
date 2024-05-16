package edu.kh.cooof.lesson.list.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.dto.Pagination;
import edu.kh.cooof.lesson.list.model.mapper.LessonListMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonListServiceImpl implements LessonListService {

	private final LessonListMapper mapper;

	// 수업 목록 조회 (검색X)
	@Override
	public Map<String, Object> selectLessonList(int cp) {

		// 수업들의 개수를 조회
		int listCount = mapper.getLessonCount();

		// listCount 결과와 cp를 이용해서
		// Pagination 객체를 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장한 객체
		Pagination pagination = new Pagination(cp, listCount);

		// 지정된 페이지 목록을 조회

		/*
		 * ROWBOUNS 객체 (Mybatis 제공 객체) - 지정된 크기(offset) 만큼 건너 뛰고 제한된 크기(limit) 만큼의 행을
		 * 조회하는 객체
		 */

		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);

		/*
		 * Mapper 메서드 호출 시 - 첫 번째 매개 변수 -> SQL에 전달할 파라미터 - 두 번째 매개 변수 -> RowBounds 객체 전달
		 */
		List<Lesson> lessonList = mapper.selectLessonList(rowBounds);

		// 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();

		map.put("pagination", pagination);
		map.put("lessonList", lessonList);

		// 결과 반환
		return map;
	}

}
