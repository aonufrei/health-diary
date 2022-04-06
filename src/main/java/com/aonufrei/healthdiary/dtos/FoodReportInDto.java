package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "Id of the consumed food")
	@JsonProperty("food_id")
	private Integer foodId;

	@Schema(description = "The amount of food metric that was consumed")
	private Integer amount;

	@Schema(description = "The metric of the amount")
	@JsonProperty("metric_id")
	private Integer metricId;

	@Schema(description = "The date when food was consumed")
	@JsonProperty("reported_date")
	private LocalDate reportedDate;

}
