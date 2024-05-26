package edu.kh.cooof.lesson.list.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.service.LessonListService;
import edu.kh.cooof.lesson.main.controller.LessonController;
import edu.kh.cooof.member.model.dto.Member;
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
    @GetMapping("/search2")
    public String searchLessons2(
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
    
    // 동기식 검색 엔드포인트 추가
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
    	return "lesson/lessonList/lessonList";
    }
    
    // 게시글 상세 조회
    @GetMapping("{lessonNo:[0-9]+}")
    public String lessonDetail(
			@PathVariable("lessonNo") int lessonNo, // 클릭된 수업의 lessonNo 저장
            @SessionAttribute(name = "loginMember", required = false) Optional<Member> loginMember, // 필수X optional 활용
            Model model,
            RedirectAttributes ra
    ) {
        // 선택한 수업 내용 상세 조회 해오기
        Lesson lesson = service.selectDetail(lessonNo);

        String path = "lesson/lessonList/lessonDetail";
        
        if (lesson == null) {
            path = "redirect:/lesson/list"; // 목록 재요청
            ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
            return path;
        }

        // 로그인한 회원이 이미 이 수업에 신청을 했는지 여부 확인
        int checkSignup = 0; // 기본값은 신청하지 않은 상태
        
        if (loginMember.isPresent()) {
            Member member = loginMember.get();
            Map<String, Integer> map = new HashMap<>();
            map.put("lessonNo", lessonNo);
            map.put("memberNo", member.getMemberNo());

            checkSignup = service.signupCheck(map);
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("checkSignup", checkSignup);
        
        return path;
    }

    
    // http://localhost/lesson/list/17/signup 요청 주소 예시
	@GetMapping("{lessonNo:[0-9]+}/signup")
	public String lessonSignup (
			@PathVariable("lessonNo") Integer lessonNo,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("lessonNo", lessonNo);
		map.put("memberNo", loginMember.getMemberNo());

		
		int result = service.lessonSignup(map);
		
		
		String path = null;
		
		if(result > 0) { // 신청 성공 시
		ra.addFlashAttribute("message", "신청 성공!");
		
		path = "redirect:/lesson/list";
		
		} else { // 신청 실패 시
			ra.addFlashAttribute("message", "신청 실패, 잔여 좌석을 확인해주세요!");
			path = "redirect:/lesson/list/" + lessonNo;
		}
		

		
		return path; // 리스트로 리다이렉트
		
	}
	
	
	
    

}
