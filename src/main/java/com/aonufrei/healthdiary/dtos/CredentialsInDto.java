package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsInDto {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@JsonProperty("person_id")
	private Integer personId;

	@NotNull
	private Authority authority;

}
