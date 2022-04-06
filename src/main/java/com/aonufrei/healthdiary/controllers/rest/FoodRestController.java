package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.services.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Food Controller")
@RestController
@RequestMapping("api/v1/food")
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
	public List<FoodDto> getAllFood(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create food")
	@PostMapping
	public Integer addFood(@RequestBody FoodInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing food")
	@PutMapping("/{id}")
	public boolean updateFood(@RequestBody FoodInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get food by id")
	@GetMapping("/{id}")
	public FoodDto getFoodById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete food by id")
	@DeleteMapping("/{id}")
	public boolean deleteFood(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
