package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Food;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Name")
	private String name;

	@Schema(description = "Metrics liked with this food")
	private Set<MetricDto> metrics;

	@Schema(description = "When food was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When food was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
