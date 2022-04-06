package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInDto {

	@Schema(description = "The main text content of the post")
	private String content;

	@Schema(description = "Path to the image of the post")
	@JsonProperty("image_path")
	private String imagePath;

	@Schema(description = "The id of the author of the post")
	@JsonProperty("author_id")
	private Integer authorId;

}
