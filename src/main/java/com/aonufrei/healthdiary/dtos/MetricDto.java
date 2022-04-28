package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodMetricType;
import com.aonufrei.healthdiary.models.Metric;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Metric type")
	private FoodMetricType type;

	@Schema(description = "Amount of calories")
	private Float calories;

	@Schema(description = "Amount of carbs")
	private Float carbs;

	@Schema(description = "Amount of proteins")
	private Float protein;

	@Schema(description = "Amount of fat")
	private Float fat;

	@Schema(description = "Target food id")
	@JsonProperty("food_id")
	private Integer foodId;

	@Schema(description = "When metric was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When metric was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
