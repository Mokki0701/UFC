package edu.kh.cooof.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.cooof.email.model.service.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"authKey"}) // model 값 session으로 변경
@Controller
@RequestMapping("email")
@RequiredArgsConstructor 
public class EmailController {

	public final EmailService service;
	
	@ResponseBody
	@PostMapping("signup")
	public int singup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		
		
		if(authKey != null) { // 인증번호가 반환되서 도랑옴
			  // == 이메일 보내기 성공		
			
			return 1;
		}
			
		
		return 0;
	}
	
	
	
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(
			@RequestBody Map<String, Object> map) {
			
			// 입력 받은 이메일, 인증 번호가 DB에 있는지 조회
			// 이메일 있고, 인증번호 일치 == 1
			// 아니면 
			return service.checkAuthKey(map);
		}
	
	
	
}
