package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;
import java.util.Map;

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

	List<BookCategory> categoryList(String storageName);

	int checkedListCount(List<String> catList);

	List<Book> checkedBookList(List<String> catList, RowBounds rowBounds);

	int searchCount(Map<String, Object> paramMap);

	int searchCount2(Map<String, Object> paramMap);

	List<Book> searchBook(Map<String, Object> paramMap, RowBounds rowBounds);

	List<Book> searchBook2(Map<String, Object> paramMap, RowBounds rowBounds);

	Book selectBook(int bookNo);

	List<Book> selectBrowsingList(int bookNo);

	List<Book> selectLoanList(int bookNo);

	List<Book> selectThemeList(int bookNo);

	List<Integer> selectAgeStatistics(int bookNo);

	List<Integer> selectYearStatistics(int bookNo);




}
