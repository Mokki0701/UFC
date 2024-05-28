package edu.kh.cooof.lib.book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.lib.book.model.service.BookReservationService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("reservation")
public class BookReservationController {

	private final BookReservationService service;
	
	@GetMapping("check")
	public String bookCheck(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("bookNo") int bookNo,
			Model model
			) {
				
		Map<String, Object> bookList = service.bookCheck(bookNo, loginMember);
		
		model.addAttribute("rentList", bookList.get("rentList"));
		model.addAttribute("totalCount", bookList.get("totalCount"));
		
		return "lib/book/bookReservation";
	}
	
	
	@GetMapping("delete")
	public String deleteReservation(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("bookNo") int bookNo,
			Model model
			) {
		
		Map<String, Object> bookList = service.reserveDelete(bookNo, loginMember);
		
		model.addAttribute("rentList", bookList.get("rentList"));
		model.addAttribute("totalCount", bookList.get("totalCount"));
		
		return "lib/book/bookReservation :: reservationBook";
	}
	
	@PostMapping("reserve")
	@ResponseBody
	public int reserveBook(
			@RequestBody Map<Integer, Integer> paramMap,
			@SessionAttribute("loginMember") Member loginMember
			) {
		List<RentBook> reserveList = new ArrayList<>();
		
		for(int i = 0; i < paramMap.size(); i++) {
			
			if(paramMap.get(i) != 0) {
				RentBook reserveBook = new RentBook();
				reserveBook.setMemberNo(loginMember.getMemberNo());
				reserveBook.setBookNo(paramMap.get(i));
				reserveBook.setRentBookCheck(2);
				reserveList.add(reserveBook);
			}
			
		}
		
		return service.reserveBook(reserveList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
