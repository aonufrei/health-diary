package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
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
