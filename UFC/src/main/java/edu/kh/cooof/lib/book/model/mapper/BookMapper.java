package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.lib.book.model.dto.BookStorageLocation;

@Mapper
public interface BookMapper {

	List<BookStorageLocation> selectBookStorage();

	int getListCount();

	List<Book> getBookList();

}
