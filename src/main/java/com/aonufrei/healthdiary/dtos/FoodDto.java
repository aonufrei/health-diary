package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Food;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

	private Integer id;

	private String name;

	private Set<MetricDto> metrics;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

	public FoodDto(Food food) {
		this.id = food.getId();
		this.name = food.getName();
		this.metrics = food.getMetrics().stream().map(MetricDto::new).collect(Collectors.toSet());
		this.createdAt = food.getCreatedAt();
		this.modifiedAt = food.getModifiedAt();
	}
}
