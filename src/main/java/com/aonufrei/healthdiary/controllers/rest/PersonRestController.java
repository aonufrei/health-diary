package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Person Controller")
@RestController
@RequestMapping("api/v1/persons")
public class PersonRestController {

	private final PersonService service;

	public PersonRestController(PersonService service) {
		this.service = service;
	}

	@Operation(summary = "Get all persons")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<PersonDto> getAllPersons(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create person")
	@PostMapping
	public Integer addPerson(@RequestBody PersonInDto dto) {
		return service.add(dto).getId();
	}

	@Operation(summary = "Update existing person")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to update")
	})
	@PutMapping("/{id}")
	public boolean updatePerson(@RequestBody PersonInDto inDto, @PathVariable Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get person by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to get")
	})
	@GetMapping("/{id}")
	public PersonDto getPersonById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete person by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the aim you want to delete")
	})
	@DeleteMapping("/{id}")
	public Boolean deletePerson(@PathVariable Integer id) {
		service.delete(id);
		return true;
	}
}
