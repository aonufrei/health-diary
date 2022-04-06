package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInDto {

	@Schema(description = "Name")
	private String name;

	@Schema(description = "Email")
	private String email;

	@Schema(description = "Date of birth")
	private LocalDate dob;

	@Schema(description = "Icon on the person account")
	@JsonProperty("image_path")
	private String imagePath;

}
