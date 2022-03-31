package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Person;
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
public class PersonDto {


	private Integer id;

	private String name;

	private String email;

	@JsonProperty("image_path")
	private String imagePath;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;


	public PersonDto(Person person) {
		this.id = person.getId();
		this.name = person.getName();
		this.email = person.getEmail();
		this.imagePath = person.getImagePath();
		this.createdAt = person.getCreatedAt();
		this.modifiedAt = person.getModifiedAt();
	}

}
