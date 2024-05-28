package edu.kh.cooof.lib.book.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lib.book.model.dto.LoanBook;

public interface BookLoanService {

	Map<String, Object> selectList(int cp);

}
