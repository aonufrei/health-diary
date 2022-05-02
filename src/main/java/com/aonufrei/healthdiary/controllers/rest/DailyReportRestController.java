package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.CaloriesPerDay;
import com.aonufrei.healthdiary.dtos.DailyReportDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.services.DailyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Daily Report Controller")
@RestController
@RequestMapping("api/v1/daily-report")
public class DailyReportRestController {

	private final DailyReportService service;

	public DailyReportRestController(DailyReportService service) {
		this.service = service;
	}

	@Operation(description = "Get daily report")
	@Parameters({
			@Parameter(name = "person", description = "Id of the person get calories report"),
			@Parameter(name = "date", description = "Date to get calories report")
	})
	@GetMapping("/day")
	public DailyReportDto getDailyReport(@RequestParam("person") Integer personId, @RequestParam("date") String stringDate) {
		return service.getDailyReportCalories(personId, convertToDate(stringDate, "date format is incorrect"));
	}

	@Operation(description = "Get report for date range")
	@Parameters({
			@Parameter(name = "person", description = "Id of the person get calories report"),
			@Parameter(name = "from_date", description = "From date of the range"),
			@Parameter(name = "to_date", description = "To date of the range")
	})
	@GetMapping("/days")
	public List<CaloriesPerDay> getCaloriesByDays(@RequestParam("person") Integer personId, @RequestParam("from_date") String stringFromDate,
	                                              @RequestParam("to_date") String stringToDate) {
		LocalDate fromDate = convertToDate(stringFromDate, "from_date format is incorrect");
		LocalDate toDate = convertToDate(stringToDate, "to_date format is incorrect");
		if (fromDate.isAfter(toDate)) {
			throw new DataValidationException("From date is after to date");
		}
		return service.getDailyReportCalories(personId, fromDate, toDate.plusDays(1));
	}

	public LocalDate convertToDate(String stringDate, String message) {
		LocalDate date;
		try {
			date = LocalDate.parse(stringDate);
		} catch (Exception e) {
			throw new DataValidationException(message);
		}
		return date;
	}
}
