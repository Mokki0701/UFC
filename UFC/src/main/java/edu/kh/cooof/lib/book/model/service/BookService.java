package edu.kh.cooof.lib.book.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lib.book.model.dto.BookCategory;


public interface BookService {

	Map<String, Object> bookList(Map<String, Object> search);

	List<String> categoryList(String storageName);

}
