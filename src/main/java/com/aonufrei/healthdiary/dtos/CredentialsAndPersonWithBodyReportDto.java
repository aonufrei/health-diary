package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsAndPersonWithBodyReportDto {

	@Schema(description = "Credentials of the new user")
	@NotNull(message = "Credentials are required")
	private CredentialsInDto credentialsInDto;

	@Schema(description = "Person with body report")
	@NotNull(message = "Person with body report is required")
	private PersonWithBodyReportInDto personWithBodyReportInDto;

}
