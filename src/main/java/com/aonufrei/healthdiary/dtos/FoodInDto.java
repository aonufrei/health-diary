package com.aonufrei.healthdiary.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodInDto {

	@Schema(description = "Name of the food")
	@NotBlank(message = "name field is required")
	private String name;

}
