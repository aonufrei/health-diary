package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodReportInDto {

	@JsonProperty("food_id")
	private Integer foodId;

	private Integer amount;

	@JsonProperty("metric_id")
	private Integer metricId;

	@JsonProperty("reported_date")
	private LocalDate reportedDate;

}
