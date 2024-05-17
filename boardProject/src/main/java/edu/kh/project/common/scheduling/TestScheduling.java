package edu.kh.project.common.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/* Spring Scheduler : 
 *  스프링에서 제공하는 일정 시간/주기 마다 예정된 코드를 실행하는 객체
 * 
 * [설정 방법]
 * 1. 프로젝트명Application.java 파일에
 *    @EnableSchduling 어노테이션 추가
 * 
 * 2. 스케쥴러 코드를 작성할 별도 클래스를 생성한 후 Bean으로 등록
 *    -> @Component 어노테이션 작성
 *    
 * 3. 해당 클래스에 @Scheduled(시간/주기) 어노테이션을 추가한 메서드 작성
 *   
 *    * 주의사항 *
 *    - 해당 메서드는 반환형이 존재해서는 안된다!! == void
 * */

@Component // bean 등록 -> 스프링이 자동으로 스케쥴링 코드를 수행하게 함
@Slf4j
public class TestScheduling {

	
	// @Scheduled() 매개 변수
	
	// 1) fixedDelay : 
	//	 - 이전 작업이 끝난 후 다음 작업이 시작 할 때 까지의 시간을 지정
	
	
	// 2) fixedRate  : 
	//   - 이전 작업이 시작한 후 다음 작업이 시작 할 때 까지의 시간을 지정
	
	
	// 3) cron
	//   - UNIX 계열 잡 스케쥴러 표현식
	
	//   - cron="초 분 시 일 월 요일 [년도]"   (일요일 1 ~ 토요일 7)
		
	//	 ex) [2024년] 화요일 5월 7일 12시 50분 0초에 수행
	//		cron="0 50 12 7 5 3 2024"
	
	//   - 특수문자 별 의미
	// * : 모든 수
	// - : 두 수 사이의 값. ex) 10-15 => 10 이상 15 이하
	// , : 특정 값 지정.    ex) 분 자리에 3,6,9,12 => 3분,6분,9분,12분에 동작
	// / : 값 증가.			ex) 0/5 => 0부터 시작해서 5씩 증가할 때 마다 수행
	
	// ? : 특별한 값 없음 (월/요일만 가능)
	// L : 마지막 (월/요일만 가능)
	
	
//	@Scheduled(fixedDelay = 5000) // ms 단위
//	@Scheduled(fixedRate = 5000) // ms 단위
	
	//         cron = "초 분 시 일 월 요일 [년도]"
//	@Scheduled(cron = "0 * * * * *") // 매 0초마다 수행 (1분 마다)
//	@Scheduled(cron = "0/10 * * * * *") // 0부터 10초마다 수행
//	@Scheduled(cron = "0 0 0 * * *") // 매일 자정마다 수행
//	@Scheduled(cron = "59 59 23 * * *") // 다음 날 되기 1초 전
	public void testMethod() {
		log.info("스케쥴러 테스트 중...");
	}
	
}




