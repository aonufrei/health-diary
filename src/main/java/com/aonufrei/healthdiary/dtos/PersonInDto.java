package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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

	private String name;

	private String email;

	private LocalDate dob;

	@JsonProperty("image_path")
	private String imagePath;

}
