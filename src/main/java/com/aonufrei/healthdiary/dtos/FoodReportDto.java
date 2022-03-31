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

	public FoodReportDto(FoodReport report) {
		this.id = report.getId();
		this.food = report.getFood() != null ? new FoodDto(report.getFood()) : null;
		this.amount = report.getAmount();
		this.metric = report.getMetric() != null ? new MetricDto(report.getMetric()) : null;
		this.reportedDate = report.getReportedDate();
		this.modifiedAt = report.getModifiedAt();
	}

}
