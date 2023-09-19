package com.practise.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(
	title="Cafe Management System",
	version="1.0.0",
	description="Software that maintains normal activity in Cafe",
	termsOfService = "Terms of Service",
	contact=@Contact(
		name = "Mohan Kadam",
		email="supportxxx@cafe.com"
	)
))
public class CafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}

}
