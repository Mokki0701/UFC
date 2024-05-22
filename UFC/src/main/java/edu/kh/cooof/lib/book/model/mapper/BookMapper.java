package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.lib.book.model.dto.Book;
import edu.kh.cooof.lib.book.model.dto.BookCategory;
import edu.kh.cooof.lib.book.model.dto.BookStorageLocation;

@Mapper
public interface BookMapper {

	List<BookStorageLocation> selectBookStorage();

	int getListCount();

	List<Book> getBookList(RowBounds rowBounds);

	List<String> categoryList(String storageName);

	int checkedListCount(String string);

	List<Book> checkedBookList(String string, RowBounds rowBounds);




}
