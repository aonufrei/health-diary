package com.aonufrei.healthdiary.dtos;

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

	@NotNull(message = "Credentials are required")
	private CredentialsInDto credentialsInDto;

	@NotNull(message = "Person with body report is required")
	private PersonWithBodyReportInDto personWithBodyReportInDto;

}
