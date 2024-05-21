package edu.kh.cooof.lib.seat.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;

@Mapper
public interface LibSeatMapper {
	

	// 현재 좌석 불러오기
	List<LibSeatDTO> getAllSeats();
    
    
	// 좌석 편집 현황 저장하기
	// 1. 모든 자리 삭제
    void deleteAllSeats();
    
    // 2. rentSeat 삭제하기
// 	void deleteRentSeat();
    
    // 3. 편집 저장하기
    void insertSeat(LibSeatDTO seat);

    // 좌석 이용하기 
	int useSeat(Map<String, Object> params);
	
	

	
}