package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.services.BodyReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/body_reports")
public class BodyReportRestController {

	private final BodyReportService service;

	public BodyReportRestController(BodyReportService service) {
		this.service = service;
	}


	@GetMapping
	public List<BodyReportDto> getAllBodyReports(@RequestParam("page") Integer page, @RequestParam("page_size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addBodyReport(@RequestBody BodyReportInDto dto) {
		return service.add(dto);
	}

	@PutMapping("/{id}")
	public boolean updateBodyReport(@RequestBody BodyReportInDto inDto, @PathVariable Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public BodyReportDto getBodyReportById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public Boolean deleteBodyReport(@PathVariable Integer id) {
		service.delete(id);
		return true;
	}
}
