package edu.kh.project.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

// @ControllerAdvice 어노테이션
// - 전역적 예외 처리
// - 리소스 바인딩
// - 메시지 컨버전

@ControllerAdvice
public class ExceptionController {
	
	// 404 발생 시 처리
	@ExceptionHandler(NoResourceFoundException.class)
	public String notFound() {
		return "error/404";
	}

	
	// 프로젝트에서 발생하는 모든 종류의 예외를 처리
	@ExceptionHandler(Exception.class)
	public String allExceptionHandler(Exception e, Model model) {
		
		e.printStackTrace();
		model.addAttribute("e", e);
		return "error/500";
	}
	
}
