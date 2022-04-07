package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.DailyReportDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.services.DailyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Daily Report Controller")
@RestController
@RequestMapping("api/v1/daily-report")
public class DailyReportRestController {

	private final DailyReportService service;

	public DailyReportRestController(DailyReportService service) {
		this.service = service;
	}

	@Operation(description = "Get daily report")
	@GetMapping
	public DailyReportDto getDailyReport(@RequestParam("person") Integer personId, @RequestParam("date") String stringDate) {
		if (personId == null)  {
			throw new DataValidationException("person is required");
		}
		LocalDate date;
		try {
			date = LocalDate.parse(stringDate);
		} catch (Exception e) {
			throw new DataValidationException("data format is incorrect");
		}
		return service.getDailyReportCalories(personId, date);
	}
}
