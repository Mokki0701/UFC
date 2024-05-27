package edu.kh.cooof.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import edu.kh.cooof.common.utility.ListUtility;

@Configuration
public class ThymeleafConfig {

	@Bean
	public ListUtility listUtility() {
		return new ListUtility();
	}
	
	@Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new SpringDataDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new ListUtilityDialect(listUtility()));
        return templateEngine;
    }
	
}
