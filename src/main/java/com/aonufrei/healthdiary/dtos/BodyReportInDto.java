package com.aonufrei.healthdiary.dtos;

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
public class BodyReportInDto {

	private Float height;

	private Float weight;

	@JsonProperty("person_id")
	private Integer personId;

	@JsonProperty("logged_time")
	private LocalDateTime loggedTime;

}
