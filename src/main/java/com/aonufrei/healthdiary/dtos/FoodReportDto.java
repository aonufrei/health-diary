package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.FoodReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodReportDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "The food related to this report")
	private FoodDto food;

	@Schema(description = "The amount of consumed food")
	private Integer amount;

	@Schema(description = "The food metric")
	private MetricDto metric;

	@Schema(description = "Identifies the meal")
	@NotNull(message = "type field is required")
	private FoodReportType type;

	@Schema(description = "The author of food report")
	@JsonProperty("person_id")
	@NotNull(message = "person_id field is required")
	private Integer personId;

	@Schema(description = "When food report was created")
	@JsonProperty("reported_date")
	private LocalDate reportedDate;

	@Schema(description = "When food report was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
