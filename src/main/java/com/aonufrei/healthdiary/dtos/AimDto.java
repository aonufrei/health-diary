package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.Aim;
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

	private AimStatus status;

	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;


	public AimDto(Aim aim) {
		this.id = aim.getId();
		this.targetWeight = aim.getTargetWeight();
		this.mentioned = aim.getMentioned();
		this.status = aim.getStatus();
		this.modifiedAt = aim.getModifiedAt();
	}

}
