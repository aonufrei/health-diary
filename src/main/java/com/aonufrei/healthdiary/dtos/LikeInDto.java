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
public class LikeInDto {

	@Schema(description = "The id of the post")
	@JsonProperty("post_id")
	private Integer postId;

	@Schema(description = "The id of the author")
	@JsonProperty("author_id")
	private Integer authorId;

}
