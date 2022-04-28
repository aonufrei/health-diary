package com.aonufrei.healthdiary.dtos;

import com.aonufrei.healthdiary.models.BodyReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BodyReportInDto {

	@Schema(description = "Value of the report")
	private Float value;

	@Schema(description = "Type of the report")
	@NotNull(message = "type field is required")
	private BodyReportType type;

	@Schema(description = "The owner of the body report")
	@JsonProperty("person_id")
	@NotNull(message = "person_id field is required")
	private Integer personId;

	@Schema(description = "The datetime when body report was created")
	@JsonProperty("logged_time")
	@NotNull(message = "logged_time is required")
	private LocalDateTime loggedTime;

}
