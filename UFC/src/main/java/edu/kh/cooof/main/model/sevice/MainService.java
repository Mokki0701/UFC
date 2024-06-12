package edu.kh.cooof.main.model.sevice;

import java.util.List;

import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.lesson.dashBoard.dto.LessonListDTO;
import edu.kh.cooof.lib.book.model.dto.Book;

public interface MainService {

	//커뮤니티탭 책 리스트 출력
	List<Book> bookList();

	//메인커뮤니트 탭 평생교육 프로그램 화면 
	List<LessonListDTO> lessonList();

	//인기 트레이너List
	List<Trainer> getTrainerList();

}
