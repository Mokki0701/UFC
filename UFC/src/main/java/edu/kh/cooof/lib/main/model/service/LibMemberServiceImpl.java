package edu.kh.cooof.lib.main.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lib.book.model.dto.LoanBook;
import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.lib.main.model.mapper.LibMemberMapper;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LibMemberServiceImpl implements LibMemberService {

	private final LibMemberMapper mapper;
	
	@Override
	public Map<String, Object> getMemberInfo(int memberNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		// 예약 책들 조회
		List<RentBook> rentList = mapper.selectRentList(memberNo);
		
		// 대출 책들 조회
		List<LoanBook> loanList = mapper.selectLoanList(memberNo);
		
		LibSeatDTO seat = mapper.selectSeat(memberNo);
		
		SpaceDTO space = mapper.selectSpace(memberNo);
		
		
		map.put("seat", seat);
		map.put("space", space);
		map.put("loanList", loanList);
		map.put("rentList", rentList);
		
		return map;
	}
	
}
