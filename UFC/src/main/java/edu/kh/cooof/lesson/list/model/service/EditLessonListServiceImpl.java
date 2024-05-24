package edu.kh.cooof.lesson.list.model.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.cooof.lesson.list.model.dto.Lesson;
import edu.kh.cooof.lesson.list.model.mapper.EditLessonListMapper;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class EditLessonListServiceImpl implements EditLessonListService {
	
	public final EditLessonListMapper mapper;
	
	// config.properties 값을 얻어와 필드에 저장
	@Value("${lesson.web-path}")
	private String webPath;
	
	@Value("${lesson.folder-path}")
	private String folderPath;
	
	
	/** 레슨 작성
	 * @throws IOException 
	 * @throws IllegalStateException 
	 *
	 */
	@Override
	public int lessonInsert(Lesson inputLesson, MultipartFile inputImg) throws IllegalStateException, IOException {
		
		
		
		// 이미지 처리
		if (inputImg != null && !inputImg.isEmpty()) { // 이미지가 존재할 경우

			String inputImgName = inputImg.getOriginalFilename();

			// 업로드 경로 지정
			File uploadDir = new File(folderPath);

			// 업로드 경로가 없을 경우 생성
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			File uploadFile = new File(uploadDir, inputImgName);
			inputImg.transferTo(uploadFile);

			// 파일명 가져와서 커맨드 객체에 넣기
			inputLesson.setImgPath(inputImgName);

			// 전부 insert
			int result = mapper.lessonInsert(inputLesson);

			// 생성된 레슨의 레슨번호 반환
			int lessonNo = inputLesson.getLessonNo();

			return lessonNo;

		} else { // 이미지가 존재하지 않을 경우 -1 반환
			return -1;
		}
	}
	
	/** 
	 * 게시글 수정
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@Override
	public int lessonUpdate(Lesson inputLesson, MultipartFile inputImg) throws IllegalStateException, IOException {
		// 이미지 처리
		if (inputImg != null && !inputImg.isEmpty()) { // 이미지가 존재할 경우

			String inputImgName = inputImg.getOriginalFilename();

			// 업로드 경로 지정
			File uploadDir = new File(folderPath);

			// 업로드 경로가 없을 경우 생성
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// 이미지 업로드
			File uploadFile = new File(uploadDir, inputImgName);
			inputImg.transferTo(uploadFile);

			// 파일명 가져와서 커맨드 객체에 넣기
			inputLesson.setImgPath(inputImgName);

			// 전부 업데이트 실행
			return mapper.lessonUpdate(inputLesson);

		} else { // 이미지가 존재하지 않을 경우 -1 반환
			return -1;
		}
		
	}
	
	

}
