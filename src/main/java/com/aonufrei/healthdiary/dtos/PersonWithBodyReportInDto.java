package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.BodyReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithBodyReportInDto {

	@Schema(description = "New person data")
	@NotNull(message = "person field is required")
	PersonInDto person;

	@Schema(description = "New person height")
	@NotNull(message = "height field is required")
	Float height;

	@Schema(description = "New person weight")
	@NotNull(message = "weight field is required")
	Float weight;

	@Schema(description = "Body reports date")
	@NotNull(message = "mentioned field is required")
	LocalDate mentioned;

}
