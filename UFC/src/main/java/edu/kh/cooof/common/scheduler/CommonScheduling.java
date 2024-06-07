package edu.kh.cooof.common.scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import edu.kh.cooof.common.scheduler.service.CommonSchedulingService;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;
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
@Slf4j // 로그 출력을 위한 어노테이션
@PropertySource("classpath:config.properties") // 외부 설정 파일을 불러오기 위한 어노테이션
public class CommonScheduling {

	@Autowired // 서비스 bean 의존성 주입(DI)
	private CommonSchedulingService service;

	@Autowired
	private SchedulingMapper mapper;

	@Value("${lesson.folder-path}")
	private String lessonLocation; // 게시글 이미지 저장 경로

//	@Scheduled(cron="0/10 * * * * *") // 0초 기준, 10초 마다 (테스트)
	@Scheduled(cron = "0 0 0 1 1 *") // 매년 1월 1일 0시 0분 0초에 실행
	public void newYear() {

		// 1. 서버 폴더 지정 ( java.io.File 이용 )
		File lessonFolder = new File(lessonLocation);

		// 2. 서버에 저장된 파일 목록 조회
		File[] lessonArr = lessonFolder.listFiles();

		// 3. 배열 -> List로 변환
		List<File> lessonList = Arrays.asList(lessonArr);

		// 4. 비어있는 리스트를 만들어서
		// 변환된 리스트를 한 곳에 저장
		List<File> serverImageList = new ArrayList<>();
		serverImageList.addAll(lessonList);

		log.info("----- 서버 이미지 목록 조회 성공 -----");

		// 5. DB에 저장된 이미지 파일 조회
		List<String> dbImageList = service.selectLessonImageNames();

		// 6. dbImageList가 null 또는 비어있을 경우 처리
		if (dbImageList == null || dbImageList.isEmpty()) {
			log.info("----- DB 이미지가 없어서 삭제 스케쥴러 종료 -----");
			return; // DB에 이미지가 없으면 메서드 종료
		}

		// 7. DB 이미지 목록과 서버 이미지 목록을 비교하여 동일한 이름의 파일을 삭제
		for (File serverFile : serverImageList) {
			if (dbImageList.contains(serverFile.getName())) { // 서버 파일이 DB 파일 목록에 있으면
				if (serverFile.delete()) { // 서버 파일 삭제 시도
					log.info("삭제한 파일: " + serverFile.getName()); // 삭제 성공 로그
				} else {
					log.warn("삭제 실패: " + serverFile.getName()); // 삭제 실패 로그
				}
			}
		}

		log.info("----- 이미지 파일 삭제 스케쥴러 종료 -----");

		// ----------------------------------------------------------------------------------------------------------------------
		// 책 대출, 예약, 희망도서 삭제
		service.deleteLibAll();

	}

	@Scheduled(cron = "0/10 * * * * *") // 0초 기준, 10초 마다
	public void lessonCloseYNCheck() { // 잔여좌석 체크 + 강사 권한 부여!!!!

		// 강사 권한 부여!!!
		int result = service.authorityCheck();
		if (result > 0) {
			log.info("----- 강사 권한 부여됨 -----");
		} else {
			log.info("----- 강사 권한 부여할 인원 없음 -----");
		}

		List<Lesson> noRemainsList = service.checkRemains();

		if (noRemainsList == null || noRemainsList.isEmpty()) {
			log.info("----- 접수중인 수업 중 잔여좌석 0인 수업 없음 -----");
			return; // 메서드 종료
		}

		for (Lesson l : noRemainsList) {
			service.setCloseYn(l.getLessonNo());
		}

	}

	// ----------------------------------------------------------------------------------------------------------------------
	// 60초에 한번 하는 스케쥴러 - MR_CHAN
	@Scheduled(cron = "0/60 * * * * *")
	public void mrChan() {

		// 좌석 이용 종료 판단을 위한 현재 시간
		Date sysdate = new Date();

		// 5분 전에 미리 알림을 보내기 위해 현재시간 +5분인 시간을 찾기
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sysdate);
		calendar.add(Calendar.MINUTE, +5);

		// -------------------- 열람실 종료 5분 전에 알람 보내기 --------------------

		// 좌석 이용 종료 5분 전에 미리 알림을 보내기 위한 시간
		Date fiveMinBefore = calendar.getTime();

		// 이용 종료 시간이 fiveMinBefore인 사람에게 메세지 보내기
		// 1. 특정인의 회원번호 조회
		// 동일한 시간에 끝난 사람이 여러명일 경우를 대비해 List로 쿼리 결과 받기

		List<Integer> result = mapper.getFiveMinBeforeMemberNo(fiveMinBefore);
		for(Integer memberNo : result) {
			
			// memberNo를 매개변수로 메세지를 보내주시면 됩니다.
			// ex) sendMessageToMember(memberNo);
			
		}

		// -------------------- 시간이 되면 열람실 이용 종료 시키기 --------------------

		// 열람실 이용 종료 시간 확인하기
		LibSeatDTO checkReadingDone = service.checkReadingDone(sysdate);
		log.info("시간 만료된 회원의 열람실 이용 종료 실행");

		// 이용 종료 시간이 sysdate인 사람이 있다면
		if (checkReadingDone != null) {

			int seatNo2 = checkReadingDone.getSeatNo2();
			int memberNo = checkReadingDone.getMemberNo();

			Map<String, Object> expiredSeat = new HashMap<>();
			expiredSeat.put("seatNo2", seatNo2);
			expiredSeat.put("memberNo", memberNo);

			// 열람실 이용 종료 실행
			int finishUsingSeat = service.finishUsingSeat(expiredSeat);


		}

	}

	// ----------------------------------------------------------------------------------------------------------------------

}
