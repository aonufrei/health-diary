package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostInDto {

	@Schema(description = "The main text content of the post")
	@NotBlank(message = "content field is required")
	private String content;

	@Schema(description = "Path to the image of the post")
	@JsonProperty("image_path")
	private String imagePath;

	@Schema(description = "The id of the author of the post")
	@JsonProperty("author_id")
	@NotNull(message = "author_id field is required")
	private Integer authorId;

}
