package edu.kh.cooof.lib.seat.model.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;

@Mapper
public interface LibSeatMapper {
	
	
	List<LibSeatDTO> getAllSeats();
    
    
    void deleteAllSeats();
    
    
    void insertSeat(LibSeatDTO seat);
}
