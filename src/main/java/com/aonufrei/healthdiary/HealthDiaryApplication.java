package com.aonufrei.healthdiary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Health Diary API", version = "0.1", description = "This application allows people to keep track of food and calories they consume to stay healthy"))
@SecurityScheme(name = "app-security", type = SecuritySchemeType.HTTP, scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class HealthDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthDiaryApplication.class, args);
	}

}
