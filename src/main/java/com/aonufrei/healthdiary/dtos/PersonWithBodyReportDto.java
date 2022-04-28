package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithBodyReportDto {

	@Schema(description = "Person")
	private PersonDto person;

	@Schema(description = "height")
	private Float height;

	@Schema(description = "weight")
	private Float weight;

}
