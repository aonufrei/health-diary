package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.models.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AimInDto {

	@NotNull
	@Size(min = 45, max = 350)
	private Float targetWeight;

	@NotNull
	@JsonProperty("person_id")
	private Integer personId;

	private LocalDate mentioned = LocalDate.now();

	private AimStatus status = AimStatus.CREATED;

}
