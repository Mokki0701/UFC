package edu.kh.project.common.scheduling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.project.common.scheduling.service.SchedulingService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@PropertySource("classpath:config.properties")
public class ImageDeleteScheduling {
	
	@Autowired // 서비스 bean 의존성 주입(DI)
	private SchedulingService service;
	

	@Value("${my.profile.folder-path}")
	private String profileLocation; // 프로필 이미지 저장 경로
	
	@Value("${my.board.folder-path}")
	private String boardLocation; // 게시글 이미지 저장 경로
	
	
//	@Scheduled(cron="0/10 * * * * *") // 0초 기준, 10초 마다 (테스트)
	@Scheduled(cron="0 0 * * * *") // 정시 마다(0분 0초 마다) (실제 운영 시)
	public void imageDelete() {
		
		// 1. 서버 폴더 지정 ( java.io.File 이용 )
		File profileFolder = new File(profileLocation);
		File boardFolder = new File(boardLocation);
		
		// 2. 서버에 저장된 파일 목록 조회
		File[] profileArr = profileFolder.listFiles();
		File[] boardArr   = boardFolder.listFiles();
		
		// 3. 배열 -> List로 변환
		List<File> profileList = Arrays.asList(profileArr);
		List<File> boardList   = Arrays.asList(boardArr);
		
		// 4. 비어있는 리스트를 만들어서
		//    변환된 리스트를 한 곳에 저장
		List<File> serverImageList = new ArrayList<>();
		
		serverImageList.addAll(profileList);
		serverImageList.addAll(boardList);
		
		log.info("----- 서버 이미지 목록 조회 성공 -----");
		
		
		// 5. DB에 저장된 회원 프로필, 게시글 이미지 목록 조회
		List<String> dbImageList = service.selectImageList();
		
		
		// 6. DB 이미지가 존재하지 않을 경우
		if(dbImageList.isEmpty()) {
			log.info("----- DB 이미지가 없어서 삭제 스케쥴러 종료 -----");
			return;
		}
		
		
		// 7. 서버 이미지 목록 순차 접근
		for(File server : serverImageList) {
			
			// 8. 서버 이미지가 DB 이미지 목록에 존재하지 않는 경우
			// File.getName() : 파일명
			if( !dbImageList.contains( server.getName() ) ) {
				
				// 9. DB에 없는 서버 이미지 삭제
				log.info("{} 삭제", server.getName()); // aaa.jpg 삭제
				server.delete();
			}
		}
		
		log.info("----- 이미지 파일 삭제 스케쥴러 종료 -----");
		
	}
	
	
	
}
