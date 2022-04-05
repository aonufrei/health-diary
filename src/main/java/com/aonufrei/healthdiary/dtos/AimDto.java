package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.AimStatus;
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
public class AimDto {

	private Integer id;

	@JsonProperty("target_weight")
	private float targetWeight;

	private LocalDate mentioned;

	@JsonProperty("person")
	private PersonDto personDto;

	private AimStatus status;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
