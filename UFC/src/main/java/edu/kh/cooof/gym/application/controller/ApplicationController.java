package edu.kh.cooof.gym.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.gym.application.model.service.ApplicationService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("apply")
@SessionAttributes({"loginMember"})
public class ApplicationController {
    
    private final ApplicationService service;

    /** 지원서 제출
     * @param position
     * @param applyRoute
     * @param resume
     * @param principal
     * @param ra
     * @return
     */
    @PostMapping("/submit-application")
    public String submitApplication(
            @RequestParam("position") String position,
            @RequestParam("apply-route") String applyRoute,
            @RequestParam("resume-cover-letter") MultipartFile resume,
            @SessionAttribute("loginMember") Member loginMember
            ) {
    	
        int result = service.submitApplication(position, applyRoute, resume);
        
        String path;

        if(result > 0) {
            path = "gym/gymMain";
        } else {
            path = "trainerSelect/apply";
        }

        return "redirect:/" + path;
    }

    /** 트레이너 승인
     * @param applicationId
     * @param ra
     * @return
     */
    @PostMapping("/accept-application")
    public String acceptApplication(
            @RequestParam("applicationNo") int applicationNo,
            RedirectAttributes ra) {
        int result = service.acceptApplication(applicationNo);

        String message;
        String path = "admin/applications";

        if(result > 0) {
            message = "승인 성공";
        } else {
            message = "승인 실패";
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/" + path;
    }

    /** 트레이너 거절
     * @param applicationId
     * @param ra
     * @return
     */
    @PostMapping("/reject-application")
    public String rejectApplication(
            @RequestParam("applicationNo") int applicationNo,
            RedirectAttributes ra) {
        int result = service.rejectApplication(applicationNo);

        String message;
        String path = "admin/applications";

        if(result > 0) {
            message = "거절 성공";
        } else {
            message = "거절 실패";
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/" + path;
    }
}
