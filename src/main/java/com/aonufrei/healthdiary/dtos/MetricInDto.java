package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricInDto {

	private FoodMetricType type;

	private Float calories;

	private Float carbs;

	private Float protein;

	private Float fat;

	@JsonProperty("food_id")
	private Integer foodId;
}
