package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.dtos.FoodWithMetricsInDto;
import com.aonufrei.healthdiary.services.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Food Controller")
@RestController
@RequestMapping("api/v1/food")
@SecurityRequirement(name = "app-security")
public class FoodRestController {

	private final FoodService service;

	public FoodRestController(FoodService service) {
		this.service = service;
	}

	@Operation(summary = "Get all food")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public List<FoodDto> getAllFood(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create food")
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Integer addFood(@RequestBody FoodInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Create food with metrics")
	@PostMapping("/with-metrics")
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean addFood(@RequestBody FoodWithMetricsInDto inDto) {
		return service.addWithMetrics(inDto);
	}

	@Operation(summary = "Update existing food")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food you want to update")
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean updateFood(@RequestBody FoodInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get food by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food you want to get")
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public FoodDto getFoodById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete food by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food you want to delete")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean deleteFood(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
