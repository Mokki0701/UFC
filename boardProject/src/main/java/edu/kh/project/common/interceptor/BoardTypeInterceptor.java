package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* Interceptor : 요청/응답 가로채는 객체 (Spring 지원)
 * 
 * Client <-> Filter <-> Dispatcher Servlet <-> Interceptor <-> Controller ....
 * 
 * 
 * * HandlerInterceptor 인터페이스를 상속 받아서 구현 해야한다
 * 
 * - preHandle  (전처리) : Dispatcher Servlet  ->  Controller 사이 수행
 * 
 * - postHandel (후처리) : Controller ->  Dispatcher Servlet  사이 수행
 * 
 * - afterCompletion (뷰 완성(forward 코드 해석) 후)
 * 						 : View Resolver -> Dispatcher Servlet 사이 수행
 *  
 * */

@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor{

	// BoardService 의존성 주입
	@Autowired
	private BoardService service;
	
	
	
	
	// 전처리
	@Override
	public boolean preHandle(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Object handler)
			throws Exception {
		
		// application scope : 
		// 	- 서버 종료 시 까지 유지되는 Servlet 내장 객체
		//  - 서버 내에 딱 한 개만 존재!
		//    --> 모든 클라이언트가 공용으로 사용
		
		// application scope 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		
		// application scope에 "boardTypeList"가 없을 경우
		if(application.getAttribute("boardTypeList") == null) {
			
			log.info("BoardTypeIntercepter - postHandle(전처리) 동작 실행");
			
			// boardTypeList 조회 서비스 호출
			List<Map<String, Object>> boardTypeList 
				= service.selectBoardTypeList();
			
			// 조회 결과를 application scope에 추가
			application.setAttribute("boardTypeList", boardTypeList);
		}
		
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	
	
	
	// 후처리
	@Override
	public void postHandle(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Object handler,
		ModelAndView modelAndView) throws Exception {


		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		

		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
	
	
	
	
	
}
