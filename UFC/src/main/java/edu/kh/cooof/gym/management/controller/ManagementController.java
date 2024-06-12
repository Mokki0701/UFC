package edu.kh.cooof.gym.management.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @Value("${gym.folder-path}")
    private String gymFolderPath;


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

    // 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
        try {
            // 지정된 디렉토리와 파일명을 결합하여 파일 경로를 만듦
            Path file = Paths.get(gymFolderPath).resolve(fileName).normalize();
            // 파일을 Resource 객체로 만듦
            Resource resource = new UrlResource(file.toUri());

            // 리소스가 존재하고 읽을 수 있는지 확인함
            if (resource.exists() && resource.isReadable()) {
                // 파일의 MIME 타입을 결정함
                String contentType = Files.probeContentType(file);
                // MIME 타입이 null이면 기본값을 설정함
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                // ResponseEntity를 만들어서 파일을 클라이언트에 반환함
                return ResponseEntity.ok()
                        // 컨텐츠 타입을 설정함
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        // 파일 다운로드를 위한 헤더를 설정함
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        // 리소스를 응답 본문으로 설정함
                        .body(resource);
            } else {
                // 리소스가 존재하지 않거나 읽을 수 없으면 404 상태 코드를 반환함
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // 예외가 발생하면 500 상태 코드를 반환함
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    // 승인 이메일
    @ResponseBody
    @PostMapping("approveApplication")
    public ResponseEntity<Map<String, Object>> approveApplication(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String memberNo = requestData.get("memberNo");
        String status = requestData.get("status");

        Map<String, Object> response = new HashMap<>();

        try {
            boolean emailSent = service.sendEmail(email, "approved");

            if (emailSent) {
            	int result = service.updateApplicationStatus(Integer.parseInt(memberNo) , "승인완료");
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "이메일 전송 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

    // 거절 이메일
    @ResponseBody
    @PostMapping("refuseApplication")
    public ResponseEntity<Map<String, Object>> refuseApplication(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String memberNo = requestData.get("memberNo");
        
        Map<String, Object> response = new HashMap<>();

        try {
            boolean emailSent = service.sendEmail(email, "refused");

            if (emailSent) {
            	service.updateApplicationStatus(Integer.parseInt(memberNo), "거절완료");
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "이메일 전송 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

    
    

    // 회원 권한 업데이트
    @ResponseBody
    @PostMapping("/updateMemberAuthority")
    public ResponseEntity<Map<String, Object>> updateMemberAuthority(@RequestBody Map<String, Integer> requestData) {
        Integer memberNo = requestData.get("memberNo");

        Map<String, Object> response = new HashMap<>();

        String path = "/management/applicationList";
        String message = null;

        try {
            int result = service.updateMemberAuthority(memberNo);
            if (result > 0) {
                message = "멤버 권한이 성공적으로 업데이트되었습니다.";
                response.put("success", true);
            } else {
                message = "멤버 권한 업데이트 실패";
                response.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "서버 오류 발생: " + e.getMessage();
            response.put("success", false);
        }

        response.put("message", message);
        response.put("redirect", path);

        return ResponseEntity.ok(response);
    }
}
