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
public class BodyReportInDto {

	@Schema(description = "New height of the person")
	private Float height;

	@Schema(description = "New weight of the person")
	private Float weight;

	@Schema(description = "The owner of the body report")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "The datetime when body report was created")
	@JsonProperty("logged_time")
	private LocalDateTime loggedTime;

}
