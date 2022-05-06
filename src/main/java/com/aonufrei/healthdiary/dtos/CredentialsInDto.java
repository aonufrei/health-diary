package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "Username")
	@NotBlank(message = "Username is required")
	private String username;

	@Schema(description = "Password")
	@NotBlank(message = "Password is required")
	private String password;

	@Schema(description = "Related person id")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "Authority of the user")
	@NotNull(message = "Authority is required")
	private Authority authority;

}
