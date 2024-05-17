package edu.kh.cooof.lib.seat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.lib.seat.model.dto.LibSeatDTO;
import edu.kh.cooof.lib.seat.model.service.LibSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("lib/seats")
public class LibSeatController {

	private final LibSeatService service;

	// 관리자 : 저장된 좌석 현황 불러오기
	@GetMapping("/data")
    public ResponseEntity<List<LibSeatDTO>> getSeatsData() {
        try {
            List<LibSeatDTO> seats = service.getAllSeats();
            log.debug("Seats: {}", seats);
            return ResponseEntity.ok(seats);
        } catch (Exception e) {
            log.error("Error fetching seats data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	// 관리자 : 좌석 변경 현황 저장하기
	@PostMapping("/save")
	public ResponseEntity<Map<String, String>> saveSeats(@RequestBody List<LibSeatDTO> seats) {
		log.debug("Received seats: {}", seats);
		Map<String, String> response = new HashMap<>();
		try {
			service.saveSeats(seats);
			response.put("message", "좌석 편집 저장 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error saving seats", e);
			response.put("message", "좌석 편집 저장 실패");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping("useSeat")
	public String useSeat(
		Model model,
		@RequestParam("dbSeatNo") int dbSeatNo,
		RedirectAttributes ra) 
	{
		
		

		
		
		return null;
	}

}
