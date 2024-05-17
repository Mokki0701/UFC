package edu.kh.cooof.lib.seat.model.service;

import java.util.List;

import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;

public interface LibSeatService {
	
	
	List<LibSeatDTO> getAllSeats();
    
    
    void saveSeats(List<LibSeatDTO> seats);
}