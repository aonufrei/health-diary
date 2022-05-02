package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.dtos.FoodWithMetricsInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodRestController.class)
class FoodRestControllerTest {

	@MockBean
	private FoodService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/food";

	FoodRestControllerTest() {
	}

	@Test
	void testGetAllFood() throws Exception {
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
	void testAddFood() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.add(inDto)).thenReturn(Food.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddFoodWithMetrics() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenReturn(true);
		mvc.perform(post(url + "/with-metrics").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().isOk());
		mvc.perform(post(url + "/with-metrics"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddFoodWithMetricsWhenError() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenThrow(DataValidationException.class);
		mvc.perform(post(url + "/with-metrics").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().isBadRequest());
		mvc.perform(post(url + "/with-metrics"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateFood() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(put(url)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put(url + "/1"))
				.andExpect(status().isBadRequest());
		mvc.perform(put(url))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testGetFoodById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(FoodDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteFood() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1"))
				.andExpect(status().isOk());
	}
}