package edu.kh.cooof.lesson.list.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.dto.LessonPagination;
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
		LessonPagination pagination = new LessonPagination(cp, listCount);

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
	
	// 수업 목록 조회 (검색O)	
	@Override
	public Map<String, Object> searchList(Map<String, Object> paramMap, int cp) {
		
		// paramMap (tags == 지정된 태그, query == 검색어)
		
		//    검색 조건에 맞는 글 조회
		int listCount = mapper.getSearchCount(paramMap);
		
		
		// 위의 결과 + cp를 이용해서
		// Pagination 객체를 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장한 객체
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		
		// 3. 지정된 페이지의 검색 결과 목록 조회
		int limit = pagination.getLimit(); 
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
		/* Mapper 메서드 호출 시
		 * - 첫 번째 매개 변수 -> SQL에 전달할 파라미터
		 * - 두 번째 매개 변수 -> RowBounds 객체 전달 
		 * */
		List<Lesson> lessonList = mapper.selectSearchList(paramMap, rowBounds);
		
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("lessonList", lessonList);
		
		// 5. 결과 반환
		return map;
	}
	
	// 수업 내용 상세 조회 해오기
	@Override
	public Lesson selectDetail(int lessonNo) {
		return mapper.selectDetail(lessonNo);
	}
	
	// 수업 신청
	@Override
	public int lessonSignup(Map<String, Integer> map) {

		// 잔여 좌석 확인하기
		int remainsCheck = mapper.remainsCheck(map);

		if (remainsCheck <= 0) { // 잔여좌석이 없을 경우

			// -1 반환
			return -1;
			
		} else { // 있을 경우
			// 수업 신청 넣기
			int result = mapper.lessonSignup(map);

			if (result > 0) { // 신청이 문제없이 진행됐을 시, 잔여 좌석 깎기
				// 잔여 좌석 깎기
				mapper.lessonCapacityDecrease(map);
			}

			// 결과 반환
			return result;
		}
		
		
		
	}
	
	// 현재 로그인한 회원이 해당 수업에 가입해 있는지 여부 확인
	@Override
	public int signupCheck(Map<String, Integer> map) {
		return mapper.signupCheck(map);
	}
	
	// 즐겨찾기 기능 구현
	@Override
	public int toggleWishlist(Map<String, Integer> map) {
		 // 기존 즐겨찾기 존재 여부 확인
        int count = mapper.checkWishlist(map);

        if (count > 0) {
            // 존재하면 삭제
            return mapper.deleteWishlist(map);
        } else {
            // 존재하지 않으면 삽입
            return mapper.insertWishlist(map);
        }
		
	}
	
	// 로그인한 회원 번호로 수업별 즐찾 여부 확인
	@Override
	public boolean isWishlisted(int lessonNo, int memberNo) {
		
		// 수업 번호와 회원 번호를 맵으로 묶고, 결과가 있을 경우 true 없을 경우 false 반환
		return mapper.checkWishlist(Map.of("lessonNo", lessonNo, "memberNo", memberNo)) > 0;
	}

}
