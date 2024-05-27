package edu.kh.cooof.lib.book.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lib.book.model.dto.RentBook;
import edu.kh.cooof.lib.book.model.mapper.BookReservationMapper;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookReservationServiceImpl implements BookReservationService {
	
	private final BookReservationMapper mapper;
	
	@Override
	public Map<String, Object> bookCheck(int bookNo, Member loginMember) {
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("bookNo", bookNo);
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		// 1. 일단 해당 회원이 이 책을 장바구니에 넣었는지 체크
		int check = mapper.checkReservation(paramMap);
		
		// 1.5 체크 한 적이 없었다? 집어넣어
		if(check == 0) {
			mapper.insertCheckBook(paramMap);
		}
		
		// 2. 이제 전체 조회
		List<RentBook> rentList = mapper.reserveRentList(loginMember.getMemberNo());
		
		int totalCount = mapper.getTotalCount(loginMember.getMemberNo());
		
		paramMap.put("rentList", rentList);
		paramMap.put("totalCount", totalCount);
		
		
		return paramMap;
	}
	
	
	
	@Override
	public Map<String, Object> reserveDelete(int bookNo, Member loginMember) {
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("bookNo", bookNo);
		paramMap.put("memberNo", loginMember.getMemberNo());
		
		mapper.reserveDelete(paramMap);
		
		List<RentBook> rentList = mapper.reserveRentList(loginMember.getMemberNo());
		
		int totalCount = mapper.getTotalCount(loginMember.getMemberNo());
		
		paramMap.put("rentList", rentList);
		paramMap.put("totalCount", totalCount);
		
		return paramMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
