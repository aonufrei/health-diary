package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.services.MetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/metrics")
public class MetricRestController {

	private final MetricService service;

	public MetricRestController(MetricService service) {
		this.service = service;
	}

	@GetMapping
	public List<MetricDto> getAllMetrics(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addMetric(@RequestBody MetricInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updateMetric(@RequestBody MetricInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public MetricDto getMetricById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteMetric(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}
	
}
