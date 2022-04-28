package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.AimStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AimDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Desired target weight")
	@JsonProperty("target_weight")
	private float targetWeight;

	@Schema(description = "When aim was assigned")
	private LocalDate mentioned;

	@Schema(description = "Aim owner")
	@JsonProperty("person")
	private PersonDto personDto;

	@Schema(description = "Stage of the aim")
	private AimStatus status;

	@Schema(description = "When aim was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
