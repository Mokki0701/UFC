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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import edu.kh.cooof.common.scheduler.mapper.SchedulingMapper;
import edu.kh.cooof.common.scheduler.service.CommonSchedulingService;
import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lib.book.model.mapper.BookLoanMapper;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.ServletContext;
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
	private ServletContext servletContext;

	@Autowired
	private SchedulingMapper mapper;

	@Autowired
	private BookLoanMapper messageMapper;

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
		
		// 탈퇴 회원 삭제
		service.deleteMemberAll();

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
			int check1 = service.setCloseYn(l.getLessonNo()); // closeYn 마감 처리
			int check2 = service.setCloseTagAdd(l.getLessonNo());// 마감중 태그 추가
			int check3 = service.removeOpenTag(l.getLessonNo()); // 모집 중 태그 삭제

			// log.info("check1 : {}, check2 : {}, check3 : {}", check1, check2, check3);
			if (check1 > 0 && check2 > 0 && check3 > 0) {
				log.info(l.getLessonNo() + "번 수업 마감 처리됨.");
			}

		}
	}


// ----------------------------------------------------------------------------------------------------------------------

}