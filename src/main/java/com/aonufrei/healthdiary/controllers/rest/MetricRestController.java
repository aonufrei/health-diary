package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.services.MetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Metric Controller")
@RestController
@RequestMapping("api/v1/metrics")
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
	public List<MetricDto> getAllMetrics(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create metric")
	@PostMapping
	public Integer addMetric(@RequestBody MetricInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing metric")
	@PutMapping("/{id}")
	public boolean updateMetric(@RequestBody MetricInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get metric by id")
	@GetMapping("/{id}")
	public MetricDto getMetricById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete metric by id")
	@DeleteMapping("/{id}")
	public boolean deleteMetric(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}
	
}
