package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Post;
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
public class PostDto {

	private Integer id;

	private String content;

	@JsonProperty("image_path")
	private String imagePath;

	@JsonProperty("likes_count")
	private Integer likesCount;

	@JsonProperty("author_id")
	private Integer authorId;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
