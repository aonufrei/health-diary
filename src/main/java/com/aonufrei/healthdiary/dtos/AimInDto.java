package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.models.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AimInDto {

	private Float targetWeight;

	@JsonProperty("person_id")
	private Integer personId;

	private LocalDate mentioned;

	private AimStatus status;

}
