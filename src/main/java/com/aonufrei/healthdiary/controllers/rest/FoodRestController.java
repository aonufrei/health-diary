package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.services.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/food")
public class FoodRestController {

	private final FoodService service;

	public FoodRestController(FoodService service) {
		this.service = service;
	}

	@GetMapping
	public List<FoodDto> getAllFood(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addFood(@RequestBody FoodInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updateFood(@RequestBody FoodInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public FoodDto getFoodById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteFood(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
