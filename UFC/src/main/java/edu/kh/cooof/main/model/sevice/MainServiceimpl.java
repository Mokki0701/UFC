package edu.kh.cooof.main.model.sevice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.main.mapper.MainPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainServiceimpl implements MainService{

	private final MainPageMapper mapper;
	
	//커뮤니티 탭 도서 리스트 출력
	@Override
		public List<Book> bookList() {
			return mapper.bookList();
		}
	
	//메인커뮤니트 탭 평생교육 프로그램 화면 
	@Override
	public List<LessonListDTO> lessonList() {
		return mapper.lessonList();
	}
	
}
