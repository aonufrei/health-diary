package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Gender;
import com.aonufrei.healthdiary.models.PhysicalActivity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInDto {

	@Schema(description = "Name")
	@NotBlank(message = "name field is required")
	private String name;

	@NotBlank(message = "email field is required")
	@Email(message = "email field should be valid email")
	@Schema(description = "Email")
	private String email;

	@NotNull(message = "dob field is required")
	@Schema(description = "Date of birth")
	private LocalDate dob;

	@NotNull(message = "gender field is required")
	@Schema(description = "Gender of the person")
	private Gender gender;

	@NotNull(message = "activity field is required")
	@Schema(description = "how often the person do physical activity")
	private PhysicalActivity activity;

	@Schema(description = "Icon on the person account")
	@JsonProperty("image_path")
	private String imagePath;

}
