package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.services.AimService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/aims")
public class AimController {

	private final AimService service;

	public AimController(AimService service) {
		this.service = service;
	}

	@GetMapping
	public List<Aim> getAllAims(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAllAims(page, pageSize);
	}

	@PostMapping
	public Aim addAim(@RequestBody Aim aim) {
		return service.addAim(aim);
	}

	@PutMapping("/{id}")
	public boolean updateAim(@RequestBody Aim aim, @PathVariable("id") Integer id) {
		return service.updateAim(id, aim);
	}

	@GetMapping("/{id}")
	public Aim getAimById(@PathVariable("id") Integer id) {
		return service.getAimById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteAim(@PathVariable("id") Integer id) {
		service.deleteAim(id);
		return true;
	}
}
