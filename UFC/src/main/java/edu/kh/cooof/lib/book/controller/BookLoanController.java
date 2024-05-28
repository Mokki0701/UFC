package edu.kh.cooof.lib.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lib.book.model.service.BookLoanService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
@RequestMapping("loan")
public class BookLoanController {

	private final BookLoanService service;
	
	@GetMapping("select")
	public String selectLoanList(
			Model model,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		Map<String, Object> map = new HashMap<>();
		
		// 일반 조회
		if(query.equals("")) {
			
			map = service.selectList(cp);
			
		}
		// 검색어 조회
		else{
			
			
		}
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));
		
		return "lib/book/bookLoan";
	}
	
}
