package edu.kh.cooof.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lib.book.model.dto.Book;

@Mapper
public interface MainPageMapper {

	//커뮤니티탭 도서 리스트 출력
	List<Book> bookList();

	//메인커뮤니트 탭 평생교육 프로그램 화면
	List<LessonListDTO> lessonList();

}
