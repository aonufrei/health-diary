package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.aonufrei.healthdiary.models.Metric;
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
public class MetricDto {

	private Integer id;

	private FoodMetricType type;

	private Float calories;

	private Float carbs;

	private Float protein;

	private Float fat;

	@JsonProperty("food_id")
	private Integer foodId;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
