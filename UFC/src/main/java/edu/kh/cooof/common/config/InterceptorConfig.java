package edu.kh.cooof.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.cooof.common.interceptor.BookCategoryInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public BookCategoryInterceptor bookCategoryInterceptor() {
		
		return new BookCategoryInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(bookCategoryInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**",
				 "/js/**",
				 "/images/**",
				 "/favicon.ico");
		
	}
	
	
}
