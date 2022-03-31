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

	private FoodMetricType name;

	private Integer value;

	@JsonProperty("food_id")
	private Integer foodId;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;


	public MetricDto(Metric metric) {
		this.id = metric.getId();
		this.name = metric.getName();
		this.value = metric.getValue();
		this.foodId = metric.getFood() != null ? metric.getFood().getId() : null;
		this.createdAt = metric.getCreatedAt();
		this.modifiedAt = metric.getModifiedAt();
	}

}
