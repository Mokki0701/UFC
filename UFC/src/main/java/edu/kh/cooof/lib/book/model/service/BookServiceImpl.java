package edu.kh.cooof.lib.book.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.cooof.lib.book.model.dto.Book;
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
	
	@Override
	public Map<String, Object> bookList(Map<String, Object> search) {
		
		List<BookStorageLocation> bookStorageLocations = new ArrayList<>();
		
		String abc;
		
		// 첫 화면에 들어왔을 때 만 실행
		if(!search.containsKey("catName")) {

			bookStorageLocations = mapper.selectBookStorage();
			
		}
		
		int listCount = 0;
		
		// 처음 목록 페이지 들어와서 카테고리 체크가 아무것도 없을 때
		if(!search.containsKey("catName")) {

			listCount = mapper.getListCount();
			
		}
		
		// 카테고리를 체크할 경우
		else {
			
			StringBuilder sb = new StringBuilder();
			
			List<String> catList = (List<String>)search.get("catList");
			
			catList.forEach(catName -> {
				
				if(sb.length() > 0) {
					sb.append(", ");
				}
				
				sb.append(catName);
				
			});
			
			abc = sb.toString();
			
			if(abc == "") {
				listCount = mapper.getListCount();
			}
			
			else {
				listCount = mapper.checkedListCount(sb.toString());
			}
			
			
		}
		
		LibPagination pagination = new LibPagination((int)search.get("cp"), listCount, (int)search.get("limit"));
		
		int offset = ((int)search.get("cp") - 1) * (int)search.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)search.get("limit"));
		
		List<Book> bookList = new ArrayList<>();
		
		// 처음 들어왔을 경우
		if(!search.containsKey("catName")) {
			
			bookList = mapper.getBookList(rowBounds);	
			
		}
		// 카테고리 체크를 했을 경우
		else {
			
			StringBuilder sb = new StringBuilder();
			
			List<String> catList = (List<String>)search.get("catList");
			
			catList.forEach(catName -> {
				
				if(sb.length() > 0) {
					sb.append(", ");
				}
				
				sb.append(catName);
				
			});
			
			String cba = sb.toString();
			
			if(cba == "") {
				bookList = mapper.getBookList(rowBounds);
			}
			else {
				bookList = mapper.checkedBookList(sb.toString(), rowBounds);				
			}
			
		}
		
		Map<String, Object> mapList = new HashMap<>();
		
		if(!search.containsKey("catName")) {

			mapList.put("bookStorageLocations", bookStorageLocations);

		}		
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
	
	@Override
	public Map<String, Object> searchBook(Map<String, Object> paramMap) {
		
		// 1. listCount 수 세기
		int listCount = 0;
		
		// 카테고리가 되있을 때
		if(paramMap.get("catList") == null) {
			listCount = mapper.searchCount(paramMap);
		}
		
		// 카테고리가 되어있지 않을 때
		else {
			
			StringBuilder sb = new StringBuilder();
			
			List<String> catList = (List<String>)paramMap.get("catList");
			
			catList.forEach(catName -> {
				
				if(sb.length() > 0) {
					sb.append(", ");
				}
				
				sb.append(catName);
				
			});
			
			paramMap.put("string", sb.toString());
			
			
			listCount = mapper.searchCount2(paramMap);
		}
		
		LibPagination pagination = new LibPagination((int)paramMap.get("cp"), listCount, (int)paramMap.get("limit"));
		
		int offset = ((int)paramMap.get("cp") - 1) * (int)paramMap.get("limit");
		
		RowBounds rowBounds = new RowBounds(offset, (int)paramMap.get("limit"));
		
		List<Book> bookList = new ArrayList<>();
		
		if(paramMap.get("catList") == null) {
			bookList = mapper.searchBook(paramMap);
		}
		else {
			StringBuilder sb = new StringBuilder();
			
			List<String> catList = (List<String>)paramMap.get("catList");
			
			catList.forEach(catName -> {
				
				if(sb.length() > 0) {
					sb.append(", ");
				}
				
				sb.append(catName);
				
			});
			paramMap.put("string", sb.toString());
			
			bookList = mapper.searchBook2(paramMap);
			
		}
		
		Map<String, Object> mapList = new HashMap<>();
			
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
		

	
	
	@Override
	public List<String> categoryList(String storageName) {
		
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
