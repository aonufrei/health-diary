package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.services.AimService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/aims")
public class AimRestController {

	private final AimService service;

	public AimRestController(AimService service) {
		this.service = service;
	}

	@GetMapping
	public List<AimDto> getAllAims(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addAim(@RequestBody AimInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updateAim(@RequestBody AimInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public AimDto getAimById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteAim(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}
}
