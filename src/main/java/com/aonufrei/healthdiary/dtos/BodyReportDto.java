package com.aonufrei.healthdiary.dtos;


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
public class BodyReportDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "New person height")
	private Float height;

	@Schema(description = "New person weight")
	private Float weight;

	@Schema(description = "Author of body report")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "Datetime when body report was created")
	@JsonProperty("logged_time")
	private LocalDateTime loggedTime;

	@Schema(description = "When body report was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When body report was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
