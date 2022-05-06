package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.services.MetricService;
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

@Tag(name = "Metric Controller")
@RestController
@RequestMapping("api/v1/metrics")
@SecurityRequirement(name = "app-security")
public class MetricRestController {

	private final MetricService service;

	public MetricRestController(MetricService service) {
		this.service = service;
	}

	@Operation(summary = "Get all metrics")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<MetricDto> getAllMetrics(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create metric")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public Integer addMetric(@RequestBody MetricInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing metric")
	@Parameters({
			@Parameter(name = "id", description = "Id of the metric you want to update")
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public boolean updateMetric(@RequestBody MetricInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get metric by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the metric you want to get")
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public MetricDto getMetricById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete metric by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the metric you want to delete")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public boolean deleteMetric(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
