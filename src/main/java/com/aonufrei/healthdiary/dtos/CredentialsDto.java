package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	private Integer id;

	private String username;

	private String password;

	@JsonProperty("person_id")
	private Integer personId;

	private Authority authority;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;
}
