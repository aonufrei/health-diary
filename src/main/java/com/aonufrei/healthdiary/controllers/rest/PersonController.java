package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.services.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

	private final PersonService service;

	public PersonController(PersonService service) {
		this.service = service;
	}


	@GetMapping
	public List<PersonDto> getAllPersons(@RequestParam("page") Integer page, @RequestParam("page_size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addPerson(@RequestBody PersonInDto dto) {
		return service.add(dto);
	}

	@PutMapping("/{id}")
	public boolean updatePerson(@RequestBody PersonInDto inDto, @PathVariable Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public PersonDto getPersonById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public Boolean deletePerson(@PathVariable Integer id) {
		service.delete(id);
		return true;
	}
}
