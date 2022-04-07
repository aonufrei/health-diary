package com.aonufrei.healthdiary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDto {

	@JsonProperty("breakfast_calories")
	private Integer breakfastCalories;
	@JsonProperty("lunch_calories")
	private Integer lunchCalories;
	@JsonProperty("dinner_calories")
	private Integer dinnerCalories;
	@JsonProperty("snacks_calories")
	private Integer snacksCalories;

	@JsonProperty("full_carbs")
	private Integer fullCarbs;
	@JsonProperty("full_proteins")
	private Integer fullProteins;
	@JsonProperty("full_fat")
	private Integer fullFat;

}
