package com.medicine.medicineapp;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class MedicineappApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(MedicineappApplication.class, args);
	}


}
