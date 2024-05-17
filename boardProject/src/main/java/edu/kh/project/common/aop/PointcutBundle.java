package edu.kh.project.common.aop;

import org.aspectj.lang.annotation.Pointcut;

// Pointcut : 실제 advice가 적용될 지점
// Pointcut 모아두는 클래스
public class PointcutBundle {

	// 작성하기 어려운 Pointcut을 미리 작성해서
	// 필요한 곳에서 클래스명.메서드명() 으로 호출해서 사용 가능
	
	// ex)  @Before("execution(* edu.kh.project..*Controller*.*(..))")
	//  ==  @Before("PointcutBundle.controllerPointCut()")
	
	@Pointcut("execution(* edu.kh.project..*Controller*.*(..))")
	public void controllerPointCut() {}
	
	
	@Pointcut("execution(* edu.kh.project..*ServiceImpl*.*(..))")
	public void serviceImplPointCut() {}
	
}
