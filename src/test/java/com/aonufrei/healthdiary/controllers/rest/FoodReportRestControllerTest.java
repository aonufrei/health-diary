package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.services.FoodReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodReportRestController.class)
class FoodReportRestControllerTest {

	@MockBean
	private FoodReportService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/food-reports";

	@Test
	void testGetAllFoodReports() throws Exception {
		assertNotNull(service);

		when(service.getAll(anyInt(), anyInt())).thenReturn(Collections.emptyList());
		mvc.perform(get(url).param("page", "0").param("size", "1"))
				.andExpect(status().isOk());
		mvc.perform(get(url).param("page", "0"))
				.andExpect(status().isOk());
		mvc.perform(get(url).param("size", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddFoodReport() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodReportInDto foodReportInDto = FoodReportInDto.builder().foodId(1).build();
		when(service.add(foodReportInDto)).thenReturn(FoodReport.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(foodReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateFoodReport() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodReportInDto foodReportInDto = FoodReportInDto.builder().foodId(1).build();
		when(service.update(1, foodReportInDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(foodReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(put(url)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(foodReportInDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put(url + "/1"))
				.andExpect(status().isBadRequest());
		mvc.perform(put(url))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testGetFoodReportById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(FoodReportDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteFoodReport() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetFoodReportByPersonIdAndDay() throws Exception {
		assertNotNull(service);

		when(service.getAllFoodReportsByPersonByDayDto(anyInt(), any(LocalDate.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/day").param("person_id", "1").param("date", "2022-01-01"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/day").param("person_id", "1").param("date", "1231231"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/day").param("date", "2022-01-01"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/day").param("person_id", "1"))
				.andExpect(status().isBadRequest());
	}

}