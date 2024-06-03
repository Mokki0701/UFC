package edu.kh.cooof.lib.book.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.lib.book.model.dto.BookCategory;
import edu.kh.cooof.lib.book.model.dto.BookStorageLocation;
import edu.kh.cooof.lib.book.model.dto.LibPagination;
import edu.kh.cooof.lib.book.model.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

	private final BookMapper mapper;
	
	public Map<String, Object> bookListSelect(Map<String, Object> search){
		
		List<BookStorageLocation> bookStorageLocations = mapper.selectBookStorage();
		
		int listCount = mapper.getListCount();;
		
		LibPagination pagination = new LibPagination((int)search.get("cp"), listCount, (int)search.get("limit"));
		
		int offset = ((int)search.get("cp") - 1) * (int)search.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)search.get("limit"));
		
		List<Book> bookList = mapper.getBookList(rowBounds);
		
		Map<String, Object> mapList = new HashMap<>();

		mapList.put("bookStorageLocations", bookStorageLocations);

	
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
	
	@Override
	public Map<String, Object> bookList(Map<String, Object> search) {
		
		int listCount = 0;
		
		List<String> catList = (List<String>)search.get("catList");
		
		
		if(catList.isEmpty()) {
			listCount = mapper.getListCount();
		}
		
		else {
			listCount = mapper.checkedListCount(catList);
		}
			
		LibPagination pagination = new LibPagination((int)search.get("cp"), listCount, (int)search.get("limit"));
		
		int offset = ((int)search.get("cp") - 1) * (int)search.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)search.get("limit"));
		
		List<Book> bookList = new ArrayList<>();

		
		if(catList.isEmpty()) {
			bookList = mapper.getBookList(rowBounds);
		}
		else {
			bookList = mapper.checkedBookList(catList, rowBounds);				
		}
			

		
		
		Map<String, Object> mapList = new HashMap<>();
			
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
	
	@Override
	public Map<String, Object> searchBook(Map<String, Object> search) {
		
		int listCount = 0;
		
		List<String> catList = (List<String>)search.get("catList");
		
		
		if(catList.isEmpty()) {
			listCount = mapper.searchCount(search);
		}
		
		else {
			listCount = mapper.searchCount2(search);
		}
			
		LibPagination pagination = new LibPagination((int)search.get("cp"), listCount, (int)search.get("limit"));
		
		int offset = ((int)search.get("cp") - 1) * (int)search.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)search.get("limit"));
		
		List<Book> bookList = new ArrayList<>();

		
		if(catList.isEmpty()) {
			bookList = mapper.searchBook(search, rowBounds);
		}
		else {
			bookList = mapper.searchBook2(search, rowBounds);				
		}
			

		
		
		Map<String, Object> mapList = new HashMap<>();
			
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
		

	
	
	@Override
	public List<BookCategory> categoryList(String storageName) {
		
		return mapper.categoryList(storageName);
	}
	
	
	@Override
	public Map<String, Object> getBookDetail(int bookNo) {
		
		// 1. 일단 선택 도서 조회
		Book book = mapper.selectBook(bookNo);
		
		// 2. 서가 브라우징 리스트 조회
		List<Book> browsingList = mapper.selectBrowsingList(bookNo);
		
		// 3. 함께 대출한 자료 리스트 조회
		List<Book> loanList = mapper.selectLoanList(bookNo);
		
		// 4. 주제가 같은 자료 리스트 조회
		List<Book> themeList = mapper.selectThemeList(bookNo);		
		
		// 6. 통계 자료
		// 6.1 연령별 통계 자료
		List<Integer> ageStatistics = mapper.selectAgeStatistics(bookNo);
		
		// 6.2 년도별 통계 자료
		List<Integer> yearStatistics = mapper.selectYearStatistics(bookNo);

		Map<String, Object> intergratedMap = new HashMap<>();
		
		intergratedMap.put("book", book);
		intergratedMap.put("browsingList", browsingList);
		intergratedMap.put("loanList", loanList);
		intergratedMap.put("themeList", themeList);
		intergratedMap.put("ageStatistics", ageStatistics);
		intergratedMap.put("yearStatistics", yearStatistics);
		
		return intergratedMap;
	}
	
}
