package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.services.FoodReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Food Report Controller")
@RestController
@RequestMapping("api/v1/food_reports")
public class FoodReportRestController {

	private final FoodReportService service;

	public FoodReportRestController(FoodReportService service) {
		this.service = service;
	}

	@Operation(summary = "Get all food reports")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<FoodReportDto> getAllFoodReports(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create food report")
	@PostMapping
	public Integer addFoodReport(@RequestBody FoodReportInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing food report")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to update")
	})
	@PutMapping("/{id}")
	public boolean updateFoodReport(@RequestBody FoodReportInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get food report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to get")
	})
	@GetMapping("/{id}")
	public FoodReportDto getFoodReportById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete food report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to delete")
	})
	@DeleteMapping("/{id}")
	public boolean deleteFoodReport(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
