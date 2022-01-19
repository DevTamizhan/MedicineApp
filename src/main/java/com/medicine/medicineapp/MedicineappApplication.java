package com.medicine.medicineapp;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class MedicineappApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(MedicineappApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer configure()
	{
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry)
			{
				registry.addMapping("/**")
						.allowedMethods("GET","PUT","POST","DELETE")
						.allowedOrigins("http://localhost:3000")
						.allowCredentials(true);
			}

		};
	}
}
