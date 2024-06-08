package edu.kh.cooof.lib.book.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lib.book.model.dto.LoanBook;

public interface BookLoanService {

	Map<String, Object> selectList(int cp);

	Map<String, Object> selectQueryList(int cp, String query);

	Map<String, Object> approveLoan(int cp, String query, int bookNo, int memberNo);

	Map<String, Object> deleteLoan(int cp, String query, int bookNo, int memberNo);

	Map<String, Object> selectReturnList(int cp);

	Map<String, Object> queryReturnList(int cp, String query);

	Map<String, Object> selectCompleteList(int cp, String query, int loanBookNo, int bookNo);

	void loanExtend(int loanBookNo, int applyMemberNo, int memberNo);

	Map<String, Object> selectHopeList(int cp);

	Map<String, Object> selectExtendList(int cp);

	Map<String, Object> queryHopeList(int cp, String query);

	int completeHopeBook(int newBookNo, int applyMemberNo, int memberNo);

	Map<String, Object> queryExtendList(int cp, String query);

	

}
