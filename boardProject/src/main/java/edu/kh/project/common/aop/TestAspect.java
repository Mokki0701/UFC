package edu.kh.project.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Component // bean 등록
//@Aspect // 공통 관심사가 작성된 클래스임을 명시 (AOP 동작용 클래스)
@Slf4j  // log를 찍을 수 있는 객체 생성 코드를 추가 (Lombok)
public class TestAspect {

	// advice : 끼워 넣을 코드(메서드)
	// Pointcut : 실제 Advice를 적용할 JoinPoint(지점)
	
	// <Pointcut 작성 방법>
	// execution( [접근제한자(생략가능)] 리턴타입 클래스명 메소드명 ([파라미터]) )
	
	// 클래스명은 패키명부터 모두 작성
	
	// * : 모든
	// edu.kh.project.. : edu.kh.project 이하 패키지
	// 메서드명(..) 에서 매개변수 .. : 매개변수 0~n개 (개수 상관 없음) 
	
	
	//@Before(포인트컷)
	@Before("execution(* edu.kh.project..*Controller*.*(..))")
	public void testAdvice() {
		log.info("------------- testAdvice() 수행됨 -------------");
	}
	
	
	@After("execution(* edu.kh.project..*Controller*.*(..))")
	public void controllerEnd(JoinPoint jp) {
		// JoinPoint 객체 : AOP 부가 기능이 적용된 대상
		
		// AOP가 적용된 클래스 이름 얻어오기
		String className = jp.getTarget().getClass().getSimpleName();
		
		// 실행된 컨트롤러 메서드 이름 얻어오기
		String methodName = jp.getSignature().getName();
		
		log.info("---------- {}.{} 수행 완료 ----------", className, methodName);
	}
	
	
	
	
	
	
}
