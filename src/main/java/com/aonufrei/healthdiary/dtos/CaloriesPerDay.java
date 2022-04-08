package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesPerDay {

	@Schema(description = "Date to scan for calories")
	private LocalDate date;

	@Schema(description = "Calories eaten at specified date")
	private Integer calories;

}
