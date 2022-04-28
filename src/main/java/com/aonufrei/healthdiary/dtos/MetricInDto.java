package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MetricInDto {

	@Schema(description = "Food metric type")
	@NotNull(message = "type field is required")
	private FoodMetricType type;

	@Schema(description = "The amount of calories that food portion contains")
	@NotNull(message = "calories field is required")
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
