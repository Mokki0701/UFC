package edu.kh.cooof.lib.book.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.LessonPagination;
import edu.kh.cooof.lib.book.model.dto.LoanBook;
import edu.kh.cooof.lib.book.model.dto.NewBook;
import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.lib.book.model.mapper.BookLoanMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookLoanServiceImpl implements BookLoanService {

	private final BookLoanMapper mapper;
	
	@Override
	public Map<String, Object> selectList(int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		int ListCount = mapper.getListCount();
		
		LessonPagination pagination = new LessonPagination(cp, ListCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<RentBook> loanList = mapper.selectList(rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectQueryList(int cp, String query) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getQueryListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<RentBook> loanList = mapper.selectQueryList(query, rowBounds);
		
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> approveLoan(int cp, String query, int bookNo, int memberNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("bookNo", bookNo);
		map.put("query", query);
		map.put("memberNo", memberNo);
		
		// 대출에 인서트
		mapper.insertLoan(map);
		
		// 예약에서 삭제
		mapper.deleteRent(map);
		
		// 대출됌 그래서 도서에서 하나 줄이기
		mapper.downBook(map);
		
		int listCount = mapper.getQueryListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<RentBook> loanList = mapper.selectQueryList(query, rowBounds);
		
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> deleteLoan(int cp, String query, int bookNo, int memberNo) {
		
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("bookNo", bookNo);
		map.put("query", query);
		map.put("memberNo", memberNo);
		
		mapper.deleteRent(map);
		
		int listCount = mapper.getQueryListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<RentBook> loanList = mapper.selectQueryList(query, rowBounds);
		
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	// ------------------------------------------------------------------------------------------
	// 여기서부턴 반납 완료 불러오기
	
	@Override
	public Map<String, Object> selectReturnList(int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getReturnListCount();
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<LoanBook> loanList = mapper.selectReturnList(rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> queryReturnList(int cp, String query) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getQueryReturnListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<LoanBook> loanList = mapper.queryReturnList(query, rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	
	}
	
	
	@Override
	public Map<String, Object> selectCompleteList(int cp, String query, int loanBookNo, int bookNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("query", query);
		map.put("loanBookNo", loanBookNo);
		map.put("bookNo", bookNo);
		
		// 반납 성공
		mapper.completeLoan(map);
		
		// 도서 카운트 낮추기
		mapper.upBook(map);
		
		int listCount = mapper.getQueryReturnListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<LoanBook> loanList = mapper.queryReturnList(query, rowBounds);
		
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public void loanExtend(int loanBookNo) {
		
		mapper.loanExtend(loanBookNo);
		
	}

	
	// -------------------------------------------------------------------------------------------------
	//여기서부턴 희망 도서 불러오기
	
	
	@Override
	public Map<String, Object> selectHopeList(int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getHopeListCount();
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<NewBook> loanList = mapper.selectHopeList(rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> queryHopeList(int cp, String query) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getQueryHopeListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<NewBook> loanList = mapper.queryHopeList(query, rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	
	@Override
	public int completeHopeBook(int newBookNo) {
		
		return mapper.completeHopeBook(newBookNo);
	}
	
	
	
	// -------------------------------------------------------------------------------------------------
	//여기서부턴 연장 신청 목록 불러오기
	@Override
	public Map<String, Object> selectExtendList(int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getExtendListCount();
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<LoanBook> loanList = mapper.selectExtendList(rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> queryExtendList(int cp, String query) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getQueryExtendListCount(query);
		
		LessonPagination pagination = new LessonPagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<LoanBook> loanList = mapper.queryExtendList(query, rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
	
	
}
