package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

	private Integer id;

	private String name;

	private String email;

	@JsonProperty("image_path")
	private String imagePath;

	private LocalDate dob;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
