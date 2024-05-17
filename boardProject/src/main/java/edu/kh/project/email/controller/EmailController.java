package edu.kh.project.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;


@SessionAttributes({"authKey"}) // model 값 session으로 변경
@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final필드에 자동으로 의존성 주입
						 // (@Autowird 생성자 방식 코드 자동 완성)
public class EmailController {

	private final EmailService service;
	
	
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		
		if(authKey != null) { // 인증번호가 반환되서 돌아옴
							  // == 이메일 보내기 성공
			return 1;
		}
		
		// 이메일 보내기 실패
		return 0;
	}
	
	
	
	/** 입력된 인증번호와 Session에 있는 인증번호 비교
	 * @param map : 전달 받은 JSON -> Map 변경하여 저장
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {
		
		// 입력 받은 이메일, 인증 번호가 DB에 있는지 조회
		// 이메일 있고, 인증번호 일치 == 1
		// 아니면 0
		return service.checkAuthKey(map);
	}
}


/* @SessionAttribute("Key")
 * - Session에 세팅된 값 중 "Key"가 일치하는 값을 얻어와
 *   매개 변수에 대입
 * */




/* @Autowired를 이용한 의존성 주입 방법은 3가지가 존재
 * 1) 필드
 * 2) setter
 * 3) 생성자 (권장)
 * 
 * 
 * Lombok 라이브러리(자주쓰는 코드 자동 완성)에서 제공하는
 * 
 * @RequiredArgsConstructor 를 이용하면
 * 
 * 필드 중
 * 1) 초기화 되지 않은 final이 붙은 필드
 * 2) 초기화 되지 않은 @NotNull이 붙은 필드
 * 
 * 1,2에 해당하는 필드에 대한 
 * @Autowired 생성자 구문을 자동 완성
 * */




// 필드에 의존성 주입하는 방법 (권장 X)
// @Autowired // 의존성 주입(DI)
// private EmailService service;

// setter 이용

// private EmailService service;

// @Autowired
// public void setService(EmailService service) {
// 	this.service = service;
// }


// 생성자
// private EmailService service;
// private MemberService service2;
//
// @Autowired
// public EmailController(EmailService service, MemberService service2) {
//	this.service = service;
//	this.service2 = service2;
// }
