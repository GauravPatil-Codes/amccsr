package com.ahmedabad.csr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
public class CsrAhmedabadApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsrAhmedabadApplication.class, args);
		// System.out.println("start");
	}



		@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry reg) {
				reg.addMapping("/**")
						.allowedOrigins(
								"https://amccsrportal.in",
								"http://amccsrportal.in"
			                   

						).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH").allowedHeaders("*")
						.allowCredentials(true);
			}
		};

	}
	

}
