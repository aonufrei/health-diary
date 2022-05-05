package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AuthorizationCredentials;
import com.aonufrei.healthdiary.dtos.CredentialsInDto;
import com.aonufrei.healthdiary.models.Credentials;
import com.aonufrei.healthdiary.services.CredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

	@PostMapping("/login")
	private ResponseEntity<String> login(HttpServletResponse servletResponse, @RequestBody AuthorizationCredentials credentials) {
		if (credentialsService.authorize(servletResponse, credentials)) {
			return ResponseEntity.ok().body("Authorized");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
