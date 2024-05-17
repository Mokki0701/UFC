package edu.kh.cooof.lib.book.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.lib.book.model.dto.BookStorageLocation;
import edu.kh.cooof.lib.book.model.dto.Pagination;
import edu.kh.cooof.lib.book.model.mapper.BookMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookMapper mapper;
	
	@Override
	public Map<String, Object> bookList(Map<String, Object> search) {
		
		
		List<BookStorageLocation> bookStorageLocations = mapper.selectBookStorage();
		
		int listCount = 0;
		
		// 처음 목록 페이지 들어와서 카테고리 체크가 아무것도 없을 때
		if((int)search.get("catNo") == 0) {

			listCount = mapper.getListCount();
			
		}
		// 카테고리를 체크할 경우
		else {
			
		}
		
		Pagination pagination = new Pagination((int)search.get("cp"), listCount, (int)search.get("limit"));
		
		int offset = ((int)search.get("cp") - 1) * (int)search.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)search.get("limit"));
		
		List<Book> bookList = new ArrayList<>();

		// 처음 들어왔을 경우
		if((int)search.get("catNo") == 0) {

			bookList = mapper.getBookList();			

		}
		// 카테고리 체크를 했을 경우
		else {
			
		}
		
		
		return null;
	}
	
}
