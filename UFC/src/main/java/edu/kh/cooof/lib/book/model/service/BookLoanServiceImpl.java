package edu.kh.cooof.lib.book.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.LessonPagination;
import edu.kh.cooof.lib.book.model.dto.LoanBook;
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
		
		List<LoanBook> loanList = mapper.selectList(rowBounds);
		
		map.put("pagination", pagination);
		map.put("loanList", loanList);
		
		return map;
	}
	
}
