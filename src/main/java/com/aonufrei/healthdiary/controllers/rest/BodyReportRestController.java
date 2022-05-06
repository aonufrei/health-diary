package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.services.BodyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Body Report Controller")
@RestController
@RequestMapping("api/v1/body-reports")
@SecurityRequirement(name = "app-security")
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
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<BodyReportDto> getAllBodyReports(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create body report")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public Integer addBodyReport(@RequestBody BodyReportInDto dto) {
		return service.add(dto).getId();
	}

	@Operation(summary = "Update existing body report")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to update")
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public boolean updateBodyReport(@RequestBody BodyReportInDto inDto, @PathVariable Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get body report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to get")
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public BodyReportDto getBodyReportById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete body report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the body report you want to delete")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public Boolean deleteBodyReport(@PathVariable Integer id) {
		service.delete(id);
		return true;
	}

	@Operation(summary = "Get weight body reports by person id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the person you want to get body report")
	})
	@GetMapping("/weight/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<BodyReportDto> getWeightBodyReportsByPerson(@PathVariable Integer id) {
		return service.getBodyReportsByPersonAndType(id, BodyReportType.WEIGHT);
	}

	@Operation(summary = "Get weight body reports by person id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the person you want to get body report")
	})
	@GetMapping("/height/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<BodyReportDto> getHeightBodyReportsByPerson(@PathVariable Integer id) {
		return service.getBodyReportsByPersonAndType(id, BodyReportType.HEIGHT);
	}

}
