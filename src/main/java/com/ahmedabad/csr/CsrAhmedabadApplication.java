package com.ahmedabad.csr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class CsrAhmedabadApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsrAhmedabadApplication.class, args);
		System.out.println("start");
	}



		@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry reg) {
				reg.addMapping("/**")
						.allowedOrigins("http://127.0.0.1:5501",
			                    "https://127.0.0.1:5501",
			                    "http://localhost:8085",
			                    "http://127.0.0.1:5500",
			                    "https://127.0.0.1:5500"
			                   

						).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH").allowedHeaders("*")
						.allowCredentials(true);
			}
		};

	}
	

}
