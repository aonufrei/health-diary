package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeInDto {

	@Schema(description = "The id of the post")
	@JsonProperty("post_id")
	@NotNull(message = "post_id field is required")
	private Integer postId;

	@Schema(description = "The id of the author")
	@JsonProperty("author_id")
	@NotNull(message = "author_id field is required")
	private Integer authorId;

}
