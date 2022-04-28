package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesPerDay {

	@Schema(description = "Date to scan for calories")
	private LocalDate date;

	@Schema(description = "Calories eaten at specified date")
	private Integer calories;

}
