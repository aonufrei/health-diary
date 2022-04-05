package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Like;
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
public class LikeDto {

	private Integer id;

	@JsonProperty("post_id")
	private Integer postId;

	@JsonProperty("author_id")
	private Integer authorId;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
