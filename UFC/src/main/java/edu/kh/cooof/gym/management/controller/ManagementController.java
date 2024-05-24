package edu.kh.cooof.gym.management.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.management.model.service.ManagementService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("management")
@RequiredArgsConstructor
@Slf4j
public class ManagementController {
	
	private final ManagementService service;

	// 지원서 리스트
	@GetMapping("applicationList")
	public String appList(
			@SessionAttribute(value = "loginMember", required = false) 
			Member loginMember, 
			RedirectAttributes ra, 
			Model model) {
		if (loginMember == null) {
			ra.addFlashAttribute("message", "로그인이 필요합니다.");
			return "redirect:/";
		}

		if (loginMember.getMemberAuthority() != 3) {
			ra.addFlashAttribute("message", "접근 권한이 없습니다.");
			return "redirect:/";
		}

	    List<Application> applications = service.selectApplications();
	    model.addAttribute("applications", applications);

	    return "gym/management/applicationList";
	}
	
	// 지원서 상세 조회
	@GetMapping("applicationDetail")
    public String appDetail(
    		@RequestParam("applicationNo") int applicationNo,
    		@SessionAttribute(value = "loginMember", required = false) 
    		Member loginMember,
    		RedirectAttributes ra,
    		Model model) {

        Application app = service.getApplicationNo(applicationNo);

        model.addAttribute("app", app);
        
        return "gym/management/applicationDetail";
    }
	
	
	@GetMapping("/download/{applicationNo}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable int applicationNo) {
		try {
			Application app = service.getApplicationNo(applicationNo);
			String resumePath = app.getResumePath();
			Path filePath = Paths.get(resumePath).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	
	
}
	












