package edu.kh.cooof.lib.book.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.cooof.lib.book.model.dto.BookCategory;
import edu.kh.cooof.lib.book.model.service.BookService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
@Slf4j
@SessionAttributes({"catList"})
public class BookController {

	private final BookService service;
	
	// 처음에 전체 북 조회
	@GetMapping("bookList")
	public String bookList(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="catNo", required = false, defaultValue= "0") int catNo,
			@RequestParam(value="storageNo", required = false, defaultValue= "0") int storageNo,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@RequestParam(value="cp", required = false , defaultValue="1") int cp,
			@RequestParam(value="searchName", required = false) String searchName,
			@RequestParam Map<String, Object> paramMap,
			Model model
			) {
		
		List<Integer> catNumbers = new ArrayList<>();
		
		paramMap.put("catNo", catNo);
		paramMap.put("cp", cp);
		paramMap.put("limit", limit);
		
		Map<String, Object> searchMap = service.bookListSelect(paramMap);
		
		model.addAttribute("bookStorageLocations", searchMap.get("bookStorageLocations"));
		model.addAttribute("bookList", searchMap.get("bookList"));
		model.addAttribute("pagination", searchMap.get("pagination"));
		model.addAttribute("catNumbers", catNumbers);
		
		
		return "lib/book/bookList";
	}
	
	@Async
	@GetMapping("category")
	public String searchCategory(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="catNo", required = false, defaultValue= "0") int catNo,
			@RequestParam(value="storageNo", required = false, defaultValue= "0") int storageNo,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@RequestParam(value="cp", required = false , defaultValue="1") int cp,
			@RequestParam Map<String, Object> paramMap,
			@RequestParam("catNumbers") List<String> catList,
			Model model
			) {
				
		// 가공해서 넘겨주어야함 catNames가 있는지랑 등등
		
		paramMap.put("catList", catList);
		paramMap.put("catNo", catNo);
		paramMap.put("cp", cp);
		paramMap.put("limit", limit);
		
		Map<String, Object> searchMap = service.bookList(paramMap);
		
		model.addAttribute("bookList", searchMap.get("bookList"));
		model.addAttribute("pagination", searchMap.get("pagination"));
		model.addAttribute("catNumbers", catList);
		
		
 		return "lib/book/bookList :: searchBook";
	}
	
	@GetMapping("search")
	public String searchBook(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="catNo", required = false, defaultValue= "0") int catNo,
			@RequestParam(value="storageNo", required = false, defaultValue= "0") int storageNo,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@RequestParam(value="cp", required = false , defaultValue="1") int cp,
			@RequestParam("query") String query,
			@RequestParam Map<String, Object> paramMap,
			@RequestParam("catNumbers") List<String> catList,
			Model model
			) {
		
		paramMap.put("catNo", catNo);
		paramMap.put("cp", cp);
		paramMap.put("limit", limit);
		paramMap.put("catList", catList);
		paramMap.put("query", query);
		

		Map<String, Object> mapList = service.searchBook(paramMap);
		
		model.addAttribute("bookList", mapList.get("bookList"));
		model.addAttribute("pagination", mapList.get("pagination"));
		model.addAttribute("catNumbers", catList);
		
		return "lib/book/bookList :: searchBook";
	}
	
	@GetMapping("catList")
	@ResponseBody
	public List<BookCategory> categoryList(
			@RequestParam("storageName") String storageName
			){
		
		return service.categoryList(storageName);
	}
	
	@GetMapping("bookDetail")
	public String bookDetail(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam("bookNo") int bookNo,
			Model model
			) {
		
		Map<String, Object> paramMap = service.getBookDetail(bookNo);
		
		model.addAttribute("book", paramMap.get("book"));
		model.addAttribute("browsingList", paramMap.get("browsingList"));
		model.addAttribute("loanList", paramMap.get("loanList"));
		model.addAttribute("themeList", paramMap.get("themeList"));
		model.addAttribute("ageStatistics", paramMap.get("ageStatistics"));
		model.addAttribute("yearStatistics", paramMap.get("yearStatistics"));
		
		model.addAttribute("yearStatisticsMax",Collections.max((List<Integer>) paramMap.get("yearStatistics")));
		model.addAttribute("yearStatisticsMin",Collections.min((List<Integer>) paramMap.get("yearStatistics")));
		model.addAttribute("ageStatisticsMax",Collections.max((List<Integer>) paramMap.get("ageStatistics")));
		model.addAttribute("ageStatisticsMin",Collections.min((List<Integer>) paramMap.get("ageStatistics")));
		
		return "lib/book/bookDetail";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
