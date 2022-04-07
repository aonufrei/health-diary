package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricInDto {

	@Schema(description = "Food metric type")
	@NotNull(message = "type field is required")
	private FoodMetricType type;

	@Schema(description = "The amount of calories that food portion contains")
	@NotNull(message = "calories field is required")
	@Min(value = 0, message = "calories field cannot be less than zero")
	private Float calories;

	@Schema(description = "The amount of carbs that food portion contains")
	@Min(value = 0, message = "carbs field cannot be less than zero")
	private Float carbs;

	@Schema(description = "The amount of protein that food portion contains")
	@Min(value = 0, message = "protein field cannot be less than zero")
	private Float protein;

	@Schema(description = "The amount of fat that food portion contains")
	@Min(value = 0, message = "fat field cannot be less than zero")
	private Float fat;

	@Schema(description = "The food owner of the metric")
	@JsonProperty("food_id")
	@NotNull(message = "food_id field is required")
	private Integer foodId;
}
