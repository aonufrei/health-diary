package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.services.BodyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Body Report Controller")
@RestController
@RequestMapping("api/v1/body_reports")
public class BodyReportRestController {

	private final BodyReportService service;

	public BodyReportRestController(BodyReportService service) {
		this.service = service;
	}

	@Operation(summary = "Get all body reports")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<BodyReportDto> getAllBodyReports(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create body report")
	@PostMapping
	public Integer addBodyReport(@RequestBody BodyReportInDto dto) {
		return service.add(dto).getId();
	}

	@Operation(summary = "Update existing body report")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to update")
	})
	@PutMapping("/{id}")
	public boolean updateBodyReport(@RequestBody BodyReportInDto inDto, @PathVariable Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get body report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to get")
	})
	@GetMapping("/{id}")
	public BodyReportDto getBodyReportById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete body report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to delete")
	})
	@DeleteMapping("/{id}")
	public Boolean deleteBodyReport(@PathVariable Integer id) {
		service.delete(id);
		return true;
	}
}
