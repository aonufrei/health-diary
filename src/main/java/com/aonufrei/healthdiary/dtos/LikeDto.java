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

	private PostDto post;

	private PersonDto author;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

	public LikeDto(Like like) {
		this.id = like.getId();
		this.post = like.getPost() != null ? new PostDto(like.getPost()) : null;
		this.author = like.getAuthor() != null ? new PersonDto(like.getAuthor()) : null;
		this.createdAt = like.getCreatedAt();
		this.modifiedAt = like.getModifiedAt();
	}
}
