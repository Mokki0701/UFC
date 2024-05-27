package edu.kh.cooof.gym.management.model.service;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.management.model.mapper.ManagementMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService{
	
	private final ManagementMapper mapper;
	
	private final JavaMailSender mailSender;
	
	private final SpringTemplateEngine templateEngine;
	
	// 지원서 리스트
	@Override
	public List<Application> selectApplications() {

		return mapper.selectApplications();
	}
	
	// 지원서 상세조회
	@Override
	public Application getApplicationNo(int applicationNo) {

		return mapper.selectApplicationNo(applicationNo);
	}
	
	// 승인 이메일 보내기
    @Override
    public boolean sendEmail(String email) {
        try {
            // MimeMessage 생성
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // 이메일 설정
            helper.setTo(email);
            helper.setSubject("트레이너 승인 완료");

            // HTML 내용 로드
            String htmlContent = loadHtml("/gymEmail");

            helper.setText(htmlContent, true);

            // CID(Content-ID)를 이용해 메일에 이미지 첨부
            helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));

            // 메일 보내기
            mailSender.send(mimeMessage);

            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }

    // HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
    public String loadHtml(String htmlName) {
        // org.thymeleaf.context.Context 선택
        Context context = new Context();
        // 필요한 변수를 context에 추가 (예: 승인 코드, 회원 이름 등)
        // context.setVariable("변수명", 값);

        // templates/gym/management/email 폴더에서 htmlName과 같은 .html 파일 내용을 읽어와 String으로 변환
        return templateEngine.process("gym/management/email" + htmlName, context);
    }
    
    
    
	
}
