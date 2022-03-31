package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInDto {

	private String content;

	@JsonProperty("image_path")
	private String imagePath;

	@JsonProperty("author_id")
	private Integer authorId;

}
