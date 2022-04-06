package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Main text content")
	private String content;

	@Schema(description = "Path to the image on the post")
	@JsonProperty("image_path")
	private String imagePath;

	@Schema(description = "The number of likes on the post")
	@JsonProperty("likes_count")
	private Integer likesCount;

	@Schema(description = "Post author id")
	@JsonProperty("author_id")
	private Integer authorId;

	@Schema(description = "When post was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When post was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
