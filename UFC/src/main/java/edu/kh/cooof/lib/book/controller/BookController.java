package edu.kh.cooof.lib.book.controller;

import java.util.ArrayList;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
@Slf4j
@SessionAttributes({"catList"})
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
	
	@Async
	@GetMapping("search")
	public String searchCategory(
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			@RequestParam(value="catNo", required = false, defaultValue= "0") int catNo,
			@RequestParam(value="storageNo", required = false, defaultValue= "0") int storageNo,
			@RequestParam(value="limit", required = false, defaultValue="10") int limit,
			@RequestParam(value="cp", required = false , defaultValue="1") int cp,
			@RequestParam("check") int check,
			@RequestParam("catName") String catName,
			@RequestParam Map<String, Object> paramMap,
			// 이거 catList Session에 저장된 값은 interceptor에서 다른 주소에 있을 때 지우겠다는거 설정해야함
			@SessionAttribute(value="catList", required = false) List<String> catList,
			Model model
			) {
		
		if(catList == null) {
			List<String> createCatList = new ArrayList<>();
			model.addAttribute("catList", createCatList);
			
			createCatList.add(catName);
			
			paramMap.put("catList", createCatList);
		}
		
		else {
			
			if(check == 1) {
				catList.add(catName);
			}
			
			else {
				int index = catList.indexOf(catName);
				catList.remove(index);
			}
			
			paramMap.put("catList", catList);
		}
		
		paramMap.put("catNo", catNo);
		paramMap.put("cp", cp);
		paramMap.put("limit", limit);
		
		Map<String, Object> searchMap = service.bookList(paramMap);
		
		model.addAttribute("bookList", searchMap.get("bookList"));
		model.addAttribute("pagination", searchMap.get("pagination"));
		
		
 		return "lib/book/bookList :: searchBook";
	}
	
	
	@GetMapping("catList")
	@ResponseBody
	public List<String> categoryList(
			@RequestParam("storageName") String storageName
			){
		
		return service.categoryList(storageName);
	}
	
	
}
