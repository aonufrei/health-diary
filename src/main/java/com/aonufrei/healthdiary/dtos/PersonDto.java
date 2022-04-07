package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Gender;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.models.PhysicalActivity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Name of the person")
	private String name;

	@Schema(description = "Email of the person")
	private String email;

	@Schema(description = "Icon on the person account")
	@JsonProperty("image_path")
	private String imagePath;

	@Schema(description = "Date of birth")
	private LocalDate dob;

	@NotNull(message = "gender field is required")
	@Schema(description = "Gender of the person")
	private Gender gender;

	@NotNull(message = "activity field is required")
	@Schema(description = "how often the person do physical activity")
	private PhysicalActivity activity;

	@Schema(description = "When person was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When person was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
