package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.lib.book.model.dto.LoanBook;
import edu.kh.cooof.lib.book.model.dto.RentBook;

@Mapper
public interface BookLoanMapper {

	int getListCount();

	List<RentBook> selectList(RowBounds rowBounds);

	int getQueryListCount(String query);

	List<RentBook> selectQueryList(String query, RowBounds rowBounds);

	void insertLoan(Map<String, Object> map);

	void deleteRent(Map<String, Object> map);

	int getReturnListCount();

	List<LoanBook> selectReturnList(RowBounds rowBounds);

	int getQueryReturnListCount(String query);

	List<LoanBook> queryReturnList(String query, RowBounds rowBounds);

	void completeLoan(Map<String, Object> map);

	void upBook(Map<String, Object> map);

	void downBook(Map<String, Object> map);

	void loanExtend(int loanBookNo);

}
