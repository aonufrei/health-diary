package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.FoodReport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodReportDto {

	private Integer id;

	private FoodDto food;

	private Integer amount;

	private MetricDto metric;

	@JsonProperty("reported_date")
	private LocalDate reportedDate;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
