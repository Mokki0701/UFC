package edu.kh.cooof.lib.book.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
