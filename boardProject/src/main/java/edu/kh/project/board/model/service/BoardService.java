package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Board;

public interface BoardService {

	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 특정 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectOne(Map<String, Integer> map);

	/** 게시글 좋아요 체크/해제
	 * @param map(memberNo, boardNo, likeCheck)
	 * @return result
	 */
	int boardLike(Map<String, Integer> map);

	/** 조회 수 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 검색 서비스
	 * @param paramMap
	 * @param cp
	 * @return map
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);
}
