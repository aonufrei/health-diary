package com.aonufrei.healthdiary.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithBodyReportDto {

	private PersonDto person;

	private Float height;

	private Float weight;

}
