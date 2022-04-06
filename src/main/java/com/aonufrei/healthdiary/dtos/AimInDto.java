package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.models.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

	@Schema(description = "Target weight in specified aim")
	private Float targetWeight;

	@Schema(description = "The owner of the aim")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "When aim was created")
	private LocalDate mentioned = LocalDate.now();

	@Schema(description = "Stage of the aim")
	private AimStatus status = AimStatus.CREATED;

}
