package edu.kh.cooof.lib.book.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lib.book.model.service.BookService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

	private final BookService service;
	
	@GetMapping("bookList")
	public String bookList(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="catNo", required = false, defaultValue= "0") int catNo,
			@RequestParam(value="storageNo", required = false, defaultValue= "0") int storageNo,
			@RequestParam(value="limit", required = false) int limit,
			@RequestParam(value="cp", required = false) int cp,
			@RequestParam(value="searchName", required = false) String searchName,
			@RequestParam Map<String, Object> paramMap,
			Model model
			) {
		
		Map<String, Object> searchMap = service.bookList(paramMap);
		
		
		
		
		return "lib/book/bookList";
	}
	
}
