package edu.kh.cooof.lib.seat.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.seat.model.mapper.LibSeatMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LibSeatServiceImpl implements LibSeatService {
    @Autowired
    private LibSeatMapper mapper;

    @Override
    public List<LibSeatDTO> getAllSeats() {
        List<LibSeatDTO> seats = mapper.getAllSeats();
        System.out.printf("Seats from mapper: %s%n", seats);  // 여기서 로그를 추가합니다.
        return seats;
    }

    @Override
    public void saveSeats(List<LibSeatDTO> seats) {
        log.debug("Deleting all seats...");
        mapper.deleteAllSeats();
        log.debug("Inserting new seats...");
        for (LibSeatDTO seat : seats) {
            log.debug("Inserting seat: {}", seat);
            mapper.insertSeat(seat);
        }
    }
}
