package edu.kh.cooof.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.cooof.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"authKey"}) // model 값 session으로 변경
@Controller
@RequestMapping("email")
@RequiredArgsConstructor 
public class EmailController {

	public final EmailService service;
	
	
	@PostMapping("signup")
	public int singup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		if(authKey != null) { // 인증번호가 반환되서 도랑옴
			  // == 이메일 보내기 성공

			return 1;
		}
			
			// 이메일 보내기 실패
			return 0;
	}
	
	
	
	
}
