package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.services.AimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Aim Controller")
@RestController
@RequestMapping("api/v1/aims")
public class AimRestController {

	private final AimService service;

	public AimRestController(AimService service) {
		this.service = service;
	}

	@Operation(summary = "Get all aims")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<AimDto> getAllAims(@RequestParam("page") Integer page,
	                               @RequestParam(name = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create aim")
	@PostMapping
	public Integer addAim(@RequestBody AimInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing aim")
	@PutMapping("/{id}")
	public boolean updateAim(@RequestBody AimInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get aim by id")
	@GetMapping("/{id}")
	public AimDto getAimById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete aim by id")
	@DeleteMapping("/{id}")
	public boolean deleteAim(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}
}
