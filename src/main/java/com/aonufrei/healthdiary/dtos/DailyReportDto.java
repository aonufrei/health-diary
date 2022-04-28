package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDto {

	@Schema(description = "Calories consumed on breakfast")
	@JsonProperty("breakfast_calories")
	private Integer breakfastCalories;
	@Schema(description = "Calories consumed on lunch")
	@JsonProperty("lunch_calories")
	private Integer lunchCalories;
	@Schema(description = "Calories consumed on dinner")
	@JsonProperty("dinner_calories")
	private Integer dinnerCalories;
	@Schema(description = "Calories consumed on snacks")
	@JsonProperty("snacks_calories")
	private Integer snacksCalories;

	@Schema(description = "Overall carbs consumed")
	@JsonProperty("full_carbs")
	private Integer fullCarbs;
	@Schema(description = "Overall protein consumed")
	@JsonProperty("full_proteins")
	private Integer fullProteins;
	@Schema(description = "Overall fat consumed")
	@JsonProperty("full_fat")
	private Integer fullFat;

}
