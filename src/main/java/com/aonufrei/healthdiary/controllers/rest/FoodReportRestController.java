package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.services.FoodReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Food Report Controller")
@RestController
@RequestMapping("api/v1/food-reports")
@SecurityRequirement(name = "app-security")
public class FoodReportRestController {

	private final FoodReportService service;

	public FoodReportRestController(FoodReportService service) {
		this.service = service;
	}

	@Operation(summary = "Get all food reports")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<FoodReportDto> getAllFoodReports(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create food report")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public Integer addFoodReport(@RequestBody FoodReportInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing food report")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food report you want to update")
	})
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public boolean updateFoodReport(@RequestBody FoodReportInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get food report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food report you want to get")
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public FoodReportDto getFoodReportById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete food report by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the food report you want to delete")
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public boolean deleteFoodReport(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

	@Operation(summary = "Get all food reports by day")
	@Parameters({
			@Parameter(name = "person_id", description = "Id of person who reported food"),
			@Parameter(name = "date", description = "Date to get food reports")
	})
	@GetMapping("/day")
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List<FoodReportDto> getFoodReportByPersonIdAndDay(@RequestParam(name = "person_id") Integer personId, @RequestParam(name = "date") String stringDate) {
		return service.getAllFoodReportsByPersonByDayDto(personId, convertToDate(stringDate, "Date is required"));
	}

	public static LocalDate convertToDate(String stringDate, String message) {
		LocalDate date;
		try {
			date = LocalDate.parse(stringDate);
		} catch (Exception e) {
			throw new DataValidationException(message);
		}
		return date;
	}

}
