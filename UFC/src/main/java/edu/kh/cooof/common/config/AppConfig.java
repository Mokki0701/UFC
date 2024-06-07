package edu.kh.cooof.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.member.model.service.MemberService;
import jakarta.servlet.ServletContext;

@Configuration
public class AppConfig {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private MemberService service;
	
	@Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            
        	Member member = service.quickLogin("testMem03@naver.com");
        	
        	servletContext.setAttribute("chiefMember", member);
        };
    }
	
}
