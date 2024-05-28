package edu.kh.cooof.lib.book.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lib.book.model.dto.RentBook;

@Mapper
public interface BookReservationMapper {

	int checkReservation(Map<String, Object> paramMap);

	void insertCheckBook(Map<String, Object> paramMap);

	List<RentBook> reserveRentList(int memberNo);

	int getTotalCount(int memberNo);

	void reserveDelete(Map<String, Object> paramMap);

	List<RentBook> searchReserve(int memberNo);

	void reserveBook(RentBook rentBook);

	int reserveListDelete(RentBook rentBook);


}
