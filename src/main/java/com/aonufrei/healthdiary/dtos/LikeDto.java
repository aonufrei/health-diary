package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Like;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Target post id")
	@JsonProperty("post_id")
	private Integer postId;

	@Schema(description = "Like author id")
	@JsonProperty("author_id")
	private Integer authorId;

	@Schema(description = "When like was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When like was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
