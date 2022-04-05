package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.services.FoodReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/food_reports")
public class FoodReportRestController {

	private final FoodReportService service;

	public FoodReportRestController(FoodReportService service) {
		this.service = service;
	}

	@GetMapping
	public List<FoodReportDto> getAllFoodReports(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addFoodReport(@RequestBody FoodReportInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updateFoodReport(@RequestBody FoodReportInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public FoodReportDto getFoodReportById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteFoodReport(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
