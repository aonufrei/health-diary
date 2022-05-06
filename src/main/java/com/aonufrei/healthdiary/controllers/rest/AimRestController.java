package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.services.AimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Aim Controller")
@RestController
@RequestMapping("api/v1/aims")
@SecurityRequirement(name = "app-security")
public class AimRestController {

	private final AimService service;

	public AimRestController(AimService service) {
		this.service = service;
	}

	@Operation(summary = "Get all aims")
	@Parameters({
			@Parameter(name = "person_id", description = "Person id to get aims"),
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<AimDto> getAllAims(@RequestParam(name = "person_id", required = false) Integer personId,
	                               @RequestParam("page") Integer page,
	                               @RequestParam(name = "size", required = false) Integer pageSize) {
		if (personId != null) {
			return service.getAllByPersonId(personId, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE), page);
		}
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create aim")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public Integer addAim(@RequestBody AimInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing aim")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to update")
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public boolean updateAim(@RequestBody AimInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get aim by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to get")
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public AimDto getAimById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Get aims by person id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the person to get aims"),
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping("/person/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<AimDto> getAimsByPersonId(@PathVariable("id") Integer id,
	                                @RequestParam("page") Integer page,
	                                @RequestParam(name = "size", required = false) Integer pageSize) {
		return service.getAllByPersonId(id, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE), page);
	}

	@Operation(summary = "Delete aim by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to delete")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public boolean deleteAim(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}
}
