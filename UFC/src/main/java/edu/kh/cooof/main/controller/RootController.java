package edu.kh.cooof.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.main.model.sevice.MainService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {
	
	private final MainService bookService;
	
	

	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
		return "redirect:/";
	}
	
	//메인커뮤니티 탭 화면 출력
	@GetMapping("")
	public String communityLib(
			Model model
			) {
		
		//도서
		List<Book> bookList = bookService.bookList();
		//프로그램
		List<LessonListDTO> lessonList = bookService.lessonList();
		
		model.addAttribute("bookList",bookList);
		model.addAttribute("lessonList",lessonList);
		
		return "index";
	}
	
	


	
	
}
