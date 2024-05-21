package edu.kh.cooof.gym.application.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.application.model.mapper.ApplicationMapper;
import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationMapper mapper;
    private final MemberService memberService;

    @Override
    public int submitApplication(String position, String applyRoute, MultipartFile resume) {
    	
        // 로그인된 사용자의 이메일
        String email = "testMem01@naver.com";
        Member member = memberService.findEmail(email);
        if (member == null) {
            return 0; 
        }

        Application application = new Application();
        application.setMemberNo(member.getMemberNo());
        application.setPosition(position);
        application.setApplyRoute(applyRoute);
        String resumePath = saveResumeFile(resume);
        application.setResumePath(resumePath);
        application.setApplyDate(new Date(System.currentTimeMillis()));

        return mapper.insertApplication(application);
    }

    
    // 이력서 파일을 저장
    private String saveResumeFile(MultipartFile resume) {
        String filePath = "path/to/save/" + resume.getOriginalFilename();
        try {
            resume.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public int acceptApplication(int applicationNo) {
        Application application = mapper.findApplicationById(applicationNo);
        if (application == null) {
            return 0; // Application not found
        }

        Member member = memberService.findById(application.getMemberNo());
        if (member == null) {
            return 0; // Member not found
        }

        int result = memberService.updateMemberAuthority(member.getMemberNo(), 2); // 트레이너 권한으로 변경
        if (result > 0) {
            // 이메일 전송 로직 추가
            String to = member.getMemberEmail();
            String subject = "트레이너 승인 완료";
            String text = "축하합니다! 트레이너로 승인되었습니다.";
            sendSimpleMessage(to, subject, text);
        }

        return result;
    }

    @Override
    public int rejectApplication(int applicationNo) {
        Application application = mapper.findApplicationById(applicationNo);
        if (application == null) {
            return 0; // Application not found
        }

        Member member = memberService.findById(application.getMemberNo());
        if (member == null) {
            return 0; // Member not found
        }

        // 이메일 전송 로직 추가
        String to = member.getMemberEmail();
        String subject = "트레이너 승인 거절";
        String text = "죄송합니다. 트레이너 승인 요청이 거절되었습니다.";
        sendSimpleMessage(to, subject, text);

        return 1; // 이메일 전송 성공으로 간주
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        // 이메일 전송 로직 구현
    }
}
