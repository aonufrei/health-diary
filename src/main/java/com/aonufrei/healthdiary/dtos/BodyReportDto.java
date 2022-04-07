package com.aonufrei.healthdiary.dtos;


import com.aonufrei.healthdiary.models.BodyReportType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyReportDto {

	@Schema(description = "Id")
	private Integer id;

	@Schema(description = "Value of the report")
	private Float value;

	@Schema(description = "Type of the report")
	private BodyReportType type;

	@Schema(description = "Author of body report")
	@JsonProperty("person_id")
	private Integer personId;

	@Schema(description = "Datetime when body report was created")
	@JsonProperty("logged_time")
	private LocalDateTime loggedTime;

	@Schema(description = "When body report was created")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "When body report was lastly modified")
	@JsonProperty("modified_at")
	private LocalDateTime modifiedAt;

}
