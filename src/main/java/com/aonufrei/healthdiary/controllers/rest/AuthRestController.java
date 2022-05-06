package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AuthorizationCredentials;
import com.aonufrei.healthdiary.dtos.CredentialsAndPersonWithBodyReportDto;
import com.aonufrei.healthdiary.dtos.CredentialsInDto;
import com.aonufrei.healthdiary.services.CredentialsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthRestController {

	private final CredentialsService credentialsService;

	public AuthRestController(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	@PostMapping("/register")
	private ResponseEntity<String> register(@RequestBody CredentialsInDto credentialsInDto) {
		if (credentialsService.register(credentialsInDto)) {
			return ResponseEntity.ok().body("Registered");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PostMapping("/register/person")
	private ResponseEntity<String> registerWithPerson(@RequestBody CredentialsAndPersonWithBodyReportDto credentialsInDto) {
		if (credentialsService.register(credentialsInDto)) {
			return ResponseEntity.ok().body("Registered");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PostMapping("/login")
	private ResponseEntity<String> login(@RequestBody AuthorizationCredentials credentials) {
		String token = credentialsService.authorize(credentials);
		if (!StringUtils.isBlank(token)) {
			return ResponseEntity.ok().body(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
