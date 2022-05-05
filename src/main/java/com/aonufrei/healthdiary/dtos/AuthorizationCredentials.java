package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationCredentials {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@JsonIgnore
	private String code;

}
