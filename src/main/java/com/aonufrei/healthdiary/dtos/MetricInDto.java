package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricInDto {

	@Schema(description = "Food metric type")
	private FoodMetricType type;

	@Schema(description = "The amount of calories that food portion contains")
	private Float calories;

	@Schema(description = "The amount of carbs that food portion contains")
	private Float carbs;

	@Schema(description = "The amount of protein that food portion contains")
	private Float protein;

	@Schema(description = "The amount of fat that food portion contains")
	private Float fat;

	@Schema(description = "The food owner of the metric")
	@JsonProperty("food_id")
	private Integer foodId;
}
