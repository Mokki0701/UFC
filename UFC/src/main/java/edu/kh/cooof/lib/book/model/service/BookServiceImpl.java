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
		
		// 전체 도서 수 검색
		
		Map<String, Object> mapList = new HashMap<>();
		
		if(!search.containsKey("catName")) {

			mapList.put("bookStorageLocations", bookStorageLocations);

		}		
		mapList.put("bookList", bookList);
		mapList.put("pagination", pagination);
		
		return mapList;
	}
	
	@Override
	public List<String> categoryList(String storageName) {
		
		return mapper.categoryList(storageName);
	}
	
	
	
}
