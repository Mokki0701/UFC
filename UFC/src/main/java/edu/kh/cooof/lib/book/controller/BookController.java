package edu.kh.cooof.lib.book.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lib.book.model.dto.BookCategory;
import edu.kh.cooof.lib.book.model.service.BookService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
@Slf4j
public class BookController {

	private final BookService service;
	
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
		
		paramMap.put("catNo", catNo);
		paramMap.put("cp", cp);
		paramMap.put("limit", limit);
		
		Map<String, Object> searchMap = service.bookList(paramMap);
		
		model.addAttribute("bookStorageLocations", searchMap.get("bookStorageLocations"));
		model.addAttribute("bookList", searchMap.get("bookList"));
		model.addAttribute("pagination", searchMap.get("pagination"));
		
		return "lib/book/bookList";
	}
	
	@GetMapping("catList")
	@ResponseBody
	public List<String> categoryList(
			@RequestParam("storageName") String storageName
			){
		
		return service.categoryList(storageName);
	}
	
	
	
}
