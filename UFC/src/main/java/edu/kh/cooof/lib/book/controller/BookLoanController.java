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
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp
			) {
		
		Map<String, Object> map = new HashMap<>();
		
		map = service.selectList(cp);
			
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));
		
		return "lib/book/bookLoan";
	}
	
	@GetMapping("querySelect")
	public String selectLoanList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			Model model
			) {
		
		Map<String, Object> map = new HashMap<>();
		
		map = service.selectQueryList(cp, query);	
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));
		
		
		return "lib/book/bookLoan :: loanBook";
	}
	
	@GetMapping("approveLoan")
	public String approveLoanList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			@RequestParam("bookNo") int bookNo,
			@RequestParam("memberNo") int memberNo,
			Model model
			) {
		
		Map<String, Object> map = new HashMap<>();
				
		map = service.approveLoan(cp, query, bookNo, memberNo);	
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));
		
		return "lib/book/bookLoan :: loanBook"; 
	}
	
	@GetMapping("deleteLoan")
	public String deleteLoanList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			@RequestParam("bookNo") int bookNo,
			@RequestParam("memberNo") int memberNo,
			Model model
			) {
		
		Map<String, Object> map = new HashMap<>();
		
		map = service.deleteLoan(cp, query, bookNo, memberNo);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));
		
		return "lib/book/bookLoan :: loanBook"; 
	}
	
	
	
	
	
	
	
}
