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

	private PersonDto author;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

	public PostDto(Post post) {
		this.id = post.getId();
		this.content = post.getContent();
		this.imagePath = post.getImagePath();
		this.likesCount = post.getLikes().size();
		this.author = post.getAuthor() != null ? new PersonDto(post.getAuthor()) : null;
		this.createdAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
	}
}
