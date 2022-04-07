package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "Food you want to add")
	@NotNull(message = "food is required")
	private FoodInDto food;

	@Schema(description = "Metrics to add to food")
	@NotEmpty(message = "Metrics list cannot be empty")
	private List<MetricInDto> metrics;

}
