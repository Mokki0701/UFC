package edu.kh.cooof.lesson.list.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.cooof.lesson.list.model.service.LessonListService;
import edu.kh.cooof.lesson.main.controller.LessonController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson/list")
@RequiredArgsConstructor
public class LessonListController {
	
	// 필드
	private final LessonListService service;

	
	
	@GetMapping("") // get 요청은 바로 레슨 메인으로
	public String lessonListMainPage(
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, // 페이지네이션용
			Model model,
			@RequestParam Map<String, Object> paramMap // key 값 받기 용
			) {
		
		// 조회 서비스 호출 후 결과 반환 받기
		Map<String, Object> map = null;
		
		// 검색이 아닌 경우
		if (paramMap.get("query") == null) {

			// 게시글 목록 조회 서비스 호출
			map = service.selectLessonList(cp);

		} else { 
			
			// 검색 서비스 호출
			map = service.searchList(paramMap, cp);
		}
		
		// 페이지네이션 모델에 등록
		model.addAttribute("pagination", map.get("pagination"));
		
		// 조회값 모델에 등록
		model.addAttribute("lessonList" , map.get("lessonList"));
		
		
		
		return "lesson/lessonList/lessonList"; // lessonList.html로 포워드
	}
	
	// 비동기식 검색 엔드포인트 추가
    @GetMapping("/search")
    public String searchLessons(
            @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
            @RequestParam Map<String, Object> paramMap,
            Model model) {
        
        // 검색 서비스 호출
        Map<String, Object> map = service.searchList(paramMap, cp);

        // 검색 결과 모델에 등록
        model.addAttribute("lessonList", map.get("lessonList"));
        model.addAttribute("pagination", map.get("pagination"));
        
        // 부분 뷰 반환
        return "lesson/lessonList/lessonList :: programs";
    }

}
