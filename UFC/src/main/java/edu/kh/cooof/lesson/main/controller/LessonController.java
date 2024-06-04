package edu.kh.cooof.lesson.main.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lesson")
@RequiredArgsConstructor
public class LessonController {

	// 파일이 저장된 디렉토리 경로를 상수로 선언함
	private static final String FILE_DIRECTORY = "C:\\mokkie\\lesson\\instReg";

	@GetMapping("") // get 요청은 바로 레슨 메인으로
	public String lessonMainPage() {

		return "lesson/lessonMain";
	}

	// 테스트용 링크
	@GetMapping("test")
	public String lessonTest() {

		return "lesson/lessonTest";
	}

	@GetMapping("download/{fileName}")
	public ResponseEntity<Resource> lessonDownload(
			@PathVariable("fileName") String fileName // URL 경로에서 파일명을 변수로 받음
	) {
		try {
			// 지정된 디렉토리와 파일명을 결합하여 파일 경로를 만듦
			Path file = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
			// 파일을 Resource 객체로 만듦
			Resource resource = new UrlResource(file.toUri());

			// 리소스가 존재하고 읽을 수 있는지 확인함
			if (resource.exists() && resource.isReadable()) {
				// 파일의 MIME 타입을 결정함
				String contentType = Files.probeContentType(file);
				// MIME 타입이 null이면 기본값을 설정함
				if (contentType == null) {
					contentType = "application/octet-stream";
				}

				// ResponseEntity를 만들어서 파일을 클라이언트에 반환함
				return ResponseEntity.ok()
						// 컨텐츠 타입을 설정함
						.contentType(org.springframework.http.MediaType.parseMediaType(contentType))
						// 파일 다운로드를 위한 헤더를 설정함
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + resource.getFilename() + "\"")
						// 리소스를 응답 본문으로 설정함
						.body(resource); 
			} else {
				// 리소스가 존재하지 않거나 읽을 수 없으면 404 상태 코드를 반환함
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			// 예외가 발생하면 500 상태 코드를 반환함
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
