package edu.kh.cooof.lib.space;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.service.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("lib/space")
public class SpaceController {

	private final SpaceService service;
	
	@PostMapping("/saveSpaceManagement")
    public ResponseEntity<Integer> saveSpaceManagement(
    		@RequestBody List<SpaceDTO> spaceList) {
        int insertedCount = service.saveSpaceManagement(spaceList);
        return ResponseEntity.ok(insertedCount);
    }
	
	@GetMapping("/getSpaces")
    public ResponseEntity<List<SpaceDTO>> getSpaces() {
        List<SpaceDTO> spaces = service.getAllSpaces();
        spaces.forEach(space -> {
            System.out.println(space); // 로그에 출력하여 데이터 확인
        });
        return ResponseEntity.ok(spaces);
    }
	
	
}
