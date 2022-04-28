package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FoodReportInDto {

	@Schema(description = "Id of the consumed food")
	@JsonProperty("food_id")
	@NotNull(message = "food_id field is required")
	private Integer foodId;

	@Schema(description = "The amount of food metric that was consumed")
	@NotNull(message = "amount field is required")
	private Integer amount;

	@Schema(description = "The metric of the amount")
	@JsonProperty("metric_id")
	@NotNull(message = "metric_id field is required")
	private Integer metricId;

	@Schema(description = "The author of food report")
	@JsonProperty("person_id")
	@NotNull(message = "person_id field is required")
	private Integer personId;

	@Schema(description = "Identifies the meal")
	@NotNull(message = "type field is required")
	private FoodReportType type;

	@Schema(description = "The date when food was consumed")
	@JsonProperty("reported_date")
	private LocalDate reportedDate = LocalDate.now();

}
