package edu.kh.cooof.lib.book.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.member.model.dto.Member;

public interface BookReservationService {

	Map<String, Object> bookCheck(int bookNo, Member loginMember);

	Map<String, Object> reserveDelete(int bookNo, Member loginMember);

	int reserveBook(List<RentBook> reserveList);


	


}
