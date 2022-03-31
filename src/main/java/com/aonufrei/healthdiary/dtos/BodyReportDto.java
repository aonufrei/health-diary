package com.aonufrei.healthdiary.dtos;


import com.aonufrei.healthdiary.models.BodyReport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyReportDto {

	private Integer id;

	private Float height;

	private Float weight;

	@JsonProperty("product_id")
	private Integer productId;

	@JsonProperty("logged_time")
	private LocalDateTime loggedTime;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;


	public BodyReportDto(BodyReport bodyReport) {
		this.id = bodyReport.getId();
		this.height = bodyReport.getHeight();
		this.weight = bodyReport.getWeight();
		this.loggedTime = bodyReport.getLoggedTime();
		this.createdAt = bodyReport.getCreatedAt();
		this.modifiedAt = bodyReport.getModifiedAt();
	}
}
