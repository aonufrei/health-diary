package com.aonufrei.healthdiary.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodWithMetricsInDto {

	@NotNull(message = "food is required")
	private FoodInDto food;

	@NotEmpty(message = "Metrics list cannot be empty")
	private List<MetricInDto> metrics;

}
