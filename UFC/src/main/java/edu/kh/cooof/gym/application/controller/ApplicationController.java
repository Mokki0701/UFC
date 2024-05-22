package edu.kh.cooof.gym.application.controller;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.application.model.service.ApplicationService;
import edu.kh.cooof.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("trainerSelect")
@RequiredArgsConstructor
@SessionAttributes("loginMember")
public class ApplicationController {

    private final ApplicationService service;

    // 지원서 제출
    @PostMapping("apply")
    public String applyForTrainer(
            @SessionAttribute("loginMember") Member loginMember,
            @RequestParam("position") String position,
            @RequestParam("apply-route") String applyRoute,
            @RequestParam("resume-cover-letter") MultipartFile resumeCoverLetter,
            RedirectAttributes ra) {

        try {
            // 로그인된 사용자 정보 가져오기
            int memberNo = loginMember.getMemberNo();

            // 이력서 파일 저장
            String resumePath = saveFile(resumeCoverLetter);

            if (resumePath == null) {
            	ra.addFlashAttribute("message", "파일 저장 실패");
                return "redirect:/trainerSelect/apply";
            }

            // Application 객체 설정
            Application inputApply = new Application();
            inputApply.setMemberNo(memberNo);
            inputApply.setPosition(position);
            inputApply.setApplyRoute(applyRoute);
            inputApply.setResumePath(resumePath);
            inputApply.setApplyDate(new Date(System.currentTimeMillis()));

            int result = service.apply(inputApply);

            String path = null;
            String message = null;

            if (result > 0) {
                path = "/";
                message = "제출되었습니다";
            } else {
                path = "apply";
                message = "제출 도중 오류 발생";
            }

            ra.addFlashAttribute("message", message);

            return "redirect:" + path;

        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("message", "서버 오류 발생: " + e.getMessage());
            return "redirect:/trainerSelect/apply";
        }
    }

    // 이력서 파일 저장
    private String saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 파일 이름에 타임스탬프 추가하여 충돌 방지
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String newFileName = timeStamp + "_" + fileName;
        String uploadDir = "C:\\uploadFiles\\UFC"; // 실제 파일 저장 경로 설정

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            File dest = new File(uploadDir, newFileName);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환 (필요시 예외 처리 로직 추가)
        }
        return newFileName;
    }
}
