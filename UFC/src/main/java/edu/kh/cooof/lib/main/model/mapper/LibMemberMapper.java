package edu.kh.cooof.lib.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.lib.book.model.dto.LoanBook;
import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.space.model.dto.SpaceDTO;

@Mapper
public interface LibMemberMapper {

	List<RentBook> selectRentList(int memberNo);

	List<LoanBook> selectLoanList(int memberNo);

	LibSeatDTO selectSeat(int memberNo);

	SpaceDTO selectSpace(int memberNo);

}
