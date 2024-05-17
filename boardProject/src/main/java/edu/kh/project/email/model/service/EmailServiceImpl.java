package edu.kh.project.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.project.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


@Transactional // 예외 발생하면 롤백할께 (기본값 커밋)
@Service // Bean 등록
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	// EmailConfig 설정이 적용된 객체(메일 보내기 가능)
	private final JavaMailSender mailSender;
	
	// 타임리프(템플릿 엔진)을 이용해서 html 코드 -> java로 변환
	private final SpringTemplateEngine templateEngine;
	
	// Mapper 의존성 주입
	private final EmailMapper mapper;
	
	
	
	// 이메일 보내기
	@Override
	public String sendEmail(String htmlName, String email) {
		
		// 6자리 난수(인증 코드) 생성
		String authKey = createAuthKey();
		
		try {
			
			// 제목
			String subject = null;
			
			switch(htmlName) {
			case "signup" : 
				subject = "[boardProject] 회원 가입 인증번호 입니다.";
				break;
			}
			
			
			// 인증 메일 보내기
			
			// MimeMessage : Java에서 메일을 보내는 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// MimeMessageHelper : 
			//	Spring에서 제공하는 메일 발송 도우미(간단 + 타임리프)
			MimeMessageHelper helper 
				= new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			// 1번 매개변수 : MimeMessage
			// 2번 매개변수 : 파일전송 사용? true / false
			// 3번 매개변수 : 문자 인코딩 지정
			
			
			helper.setTo(email); // 받는 사람 이메일 지정
			helper.setSubject(subject); // 이메일 제목 지정
			
			helper.setText( loadHtml(authKey,htmlName) , true);
			// HTML 코드 해석 여부 true (innerHTML 해석)
			
			// CID(Content-ID)를 이용해 메일에 이미지 첨부
			// (파일첨부와는 다름, 이메일 내용에 사용할 이미지 첨부)
			helper.addInline("logo", 
						new ClassPathResource("static/images/logo.jpg"));
			
			// -> 로고 이미지를 메일 내용에 첨부하는데
			//   사용하고 싶으면 "logo"라는 id를 작성해라
			
			// 메일 보내기
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		// 이메일 + 인증 번호를 "TB_AUTH_KEY" 테이블 저장
		
		Map<String, String> map = new HashMap<>(); 
		map.put("authKey", authKey);
		map.put("email", email);
		
		// 1) 해당 이메일이 DB에 존재하는 경우가 있을 수 있기 때문에
		//	  수정(update)을 먼저 진행
		//    -> 1 반환 == 업데이트 성공 == 이미 존재해서 인증번호 변경
		//    -> 0 반환 == 업데이트 실패 == 이메일 존재 X
		//       --> INSERT 시도
		
		int result = mapper.updateAuthKey(map);
		
		
		// 2) 1번 update 실패 시 insert 시도
		if(result == 0) {
			result = mapper.insertAuthKey(map);
		}
		
		// 수정, 삭제 후에도 result가 0 == 실패
		if(result == 0) return null;
		
		// 성공
		return authKey; // 오류 없이 전송되면 authKey 반환
	}
	
	
	// HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
	public String loadHtml(String authKey, String htmlName) {
		
		// org.tyhmeleaf.Context 선택!!
		Context context = new Context();
		
		//타임리프가 적용된 HTML에서 사용할 값 추가
		context.setVariable("authKey", authKey);
		
		// templates/email 폴더에서 htmlName과 같은 
		// .html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("email/" + htmlName, context);
		
	}
	
	
	
	
	
    /** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
     * @return authKey
     */
    public String createAuthKey() {
    	String key = "";
        for(int i=0 ; i< 6 ; i++) {
            
            int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
            
            if(sel1 == 0) {
                
                int num = (int)(Math.random() * 10); // 0~9
                key += num;
                
            }else {
                
                char ch = (char)(Math.random() * 26 + 65); // A~Z
                
                int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
                
                if(sel2 == 0) {
                    ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
                }
                
                key += ch;
            }
            
        }
        return key;
    }
	
    
    // 이메일, 인증번호 확인
    @Override
    public int checkAuthKey(Map<String, Object> map) {
    	return mapper.checkAuthKey(map);
    }
	
	
}

/* Google SMTP를 이용한 이메일 전송하기
 * 
 * - SMTP(Simple Mail Transfer Protocol, 간단한 메일 전송 규약)
 *  --> 이메일 메시지를 보내고 받을 때 사용하는 약속(규약, 방법)
 * 
 * 
 * - Google SMTP
 * 
 *   Java Mail Sender  ->  Google SMTP  ->  대상에게 이메일 전송
 *   
 * - Java Mail Sender에 Google SMTP 이용 설정 추가
 *  1) config.properties 내용 추가
 *  2) EmailConfig.java
 * */






