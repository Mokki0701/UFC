package edu.kh.cooof.lib.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		Map<String, Object> map4 = new HashMap<>();
		
		map = service.selectList(cp);
		map2 = service.selectReturnList(cp);
		map3 = service.selectHopeList(cp);
		map4 = service.selectExtendList(cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loanList", map.get("loanList"));

		model.addAttribute("returnList", map2.get("loanList"));
		model.addAttribute("returnPagination", map2.get("pagination"));
		
		model.addAttribute("hopePagination", map3.get("pagination"));
		model.addAttribute("hopeList", map3.get("loanList"));
		
		model.addAttribute("extendList", map4.get("loanList"));
		model.addAttribute("extendPagination", map4.get("pagination"));
		
		return "lib/book/bookLoan";
	}
	
	// ------------------------------------------------------------------
	
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
	
	// ------------------------------------------------------------------
	
	@GetMapping("returnSelect")
	public String selectReturnList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			Model model
			) {
		
		if(query.equals("")) {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.selectReturnList(cp);	
			
			model.addAttribute("returnPagination", map.get("pagination"));
			model.addAttribute("returnList", map.get("loanList"));
			
		}
		
		else {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.queryReturnList(cp, query);	
			
			model.addAttribute("returnPagination", map.get("pagination"));
			model.addAttribute("returnList", map.get("loanList"));
			
		}
		
		
		
		return "lib/book/bookLoan :: returnBook";
	}
	
	@GetMapping("completeSelect")
	public String selectCompleteList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			@RequestParam("loanBookNo") int loanBookNo,
			@RequestParam("bookNo") int bookNo,
			Model model
			) {
		
		Map<String, Object> map = new HashMap<>();
		
		map = service.selectCompleteList(cp, query, loanBookNo, bookNo);	
		
		model.addAttribute("returnPagination", map.get("pagination"));
		model.addAttribute("returnList", map.get("loanList"));
		
		
		return "lib/book/bookLoan :: returnBook";	
	}
		
	@GetMapping("extendComplete")
	@ResponseBody
	public void extendLoan(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("loanBookNo") int loanBookNo
			) {
		
		service.loanExtend(loanBookNo);
		
	}
	
	// ------------------------------------------------------------------
	// 희망 도서
	
	@GetMapping("hope")
	public String selectHopeList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			Model model
			) {
		
		if(query.equals("")) {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.selectHopeList(cp);	
			
			model.addAttribute("hopePagination", map.get("pagination"));
			model.addAttribute("hopeList", map.get("loanList"));
			
		}
		
		else {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.queryHopeList(cp, query);	
			
			model.addAttribute("hopePagination", map.get("pagination"));
			model.addAttribute("hopeList", map.get("loanList"));
			
		}
		
		return "lib/book/bookLoan :: hopeBook";
	}
	
	@GetMapping("hopeComplete")
	@ResponseBody
	public int completeHopeBook(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("newBookNo") int newBookNo,
			Model model
			) {
		
		return service.completeHopeBook(newBookNo);
	}
	
	// ------------------------------------------------------------------
	// 연장 도서
	
	@GetMapping("extend")
	public String selectExtendList(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="query", required = false, defaultValue = "") String query,
			Model model
			) {
		
		if(query.equals("")) {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.selectExtendList(cp);	
			
			model.addAttribute("extendPagination", map.get("pagination"));
			model.addAttribute("extendList", map.get("loanList"));
			
		}
		
		else {
			
			Map<String, Object> map = new HashMap<>();
			
			map = service.queryExtendList(cp, query);	
			
			model.addAttribute("extendPagination", map.get("pagination"));
			model.addAttribute("extendList", map.get("loanList"));
			
		}
		
		return "lib/book/bookLoan :: extendBook";
	}
	
	
	
	
	
	
}
