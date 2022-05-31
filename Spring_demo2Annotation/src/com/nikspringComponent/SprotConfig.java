package com.nikspringComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@ComponentScan("com.nikspringComponent")
@PropertySource("classpath:value.properties")
public class SprotConfig {

	// define the bean for sadFortuneService
	@Bean
	public FortuneService sadFortuneService() {
		return new SadFortuneService();
	}

	@Bean
	public Coach swimCoach() {
		return new SwimCoach(sadFortuneService());

	}
}
