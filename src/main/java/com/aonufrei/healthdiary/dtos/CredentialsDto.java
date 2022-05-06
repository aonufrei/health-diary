package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Username")
	private String username;

	@Schema(description = "Password")
	private String password;

	@Schema(description = "Related person to user")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "Authority")
	private Authority authority;

	@Schema(description = "User was created")
	private LocalDateTime createdAt;

	@Schema(description = "User lastly updated")
	private LocalDateTime modifiedAt;
}
