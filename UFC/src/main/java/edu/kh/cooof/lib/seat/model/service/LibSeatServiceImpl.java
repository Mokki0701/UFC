package edu.kh.cooof.lib.seat.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.seat.model.mapper.LibSeatMapper;
import edu.kh.cooof.member.model.dto.Member;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LibSeatServiceImpl implements LibSeatService {
    @Autowired
    private LibSeatMapper mapper;

    // 열람실 편집 현황 불러오기
    @Override
    public List<LibSeatDTO> getAllSeats() {
        List<LibSeatDTO> seats = mapper.getAllSeats();
        return seats;
    }

    // 좌석 배치 편집 현황 저장하기
    @Override
    public void saveSeats(List<LibSeatDTO> seats) {
        log.debug("Deleting all seats...");
        mapper.deleteAllSeats();
//        mapper.deleteRentSeat();
        log.debug("Inserting new seats...");
        for (LibSeatDTO seat : seats) {
        	log.debug("Inserting seat: {}", seat); 
            mapper.insertSeat(seat);
        }
    }
    
    // 좌석 예약하기
    @Override
    public int useSeat(int seatNo2, int memberNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("seatNo2", seatNo2);
        params.put("memberNo", memberNo);

        return mapper.useSeat(params);
    }
}