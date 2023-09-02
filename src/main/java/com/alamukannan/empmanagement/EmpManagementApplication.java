package com.alamukannan.empmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmpManagementApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext app=	SpringApplication.run(EmpManagementApplication.class, args);
		app.getEnvironment().setActiveProfiles("dev");
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
