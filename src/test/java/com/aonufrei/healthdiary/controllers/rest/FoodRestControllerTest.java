package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.dtos.FoodWithMetricsInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
	@WithMockUser(authorities = "ADMIN")
	void testGetAllFoodForAdmin() throws Exception {
		testGetAllFood();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddFoodForAdmin() throws Exception {
		testAddFood();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddFoodWithMetricsForAdmin() throws Exception {
		testAddFoodWithMetrics();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddFoodWithMetricsWhenErrorForAdmin() throws Exception {
		testAddFoodWithMetricsWhenError();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testUpdateFoodForAdmin() throws Exception {
		testUpdateFood();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetFoodByIdForAdmin() throws Exception {
		testGetFoodById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteFoodForAdmin() throws Exception {
		testDeleteFood();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetAllFoodForUser() throws Exception {
		testGetAllFood();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddFoodForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.add(inDto)).thenReturn(Food.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddFoodWithMetricsForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenReturn(true);
		mvc.perform(post(url + "/with-metrics").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddFoodWithMetricsWhenErrorForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenThrow(DataValidationException.class);
		mvc.perform(post(url + "/with-metrics").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testUpdateFoodForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1").with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetFoodByIdForUser() throws Exception {
		testGetFoodById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testDeleteFoodForUser() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	private void testGetAllFood() throws Exception {
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

	private void testAddFood() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.add(inDto)).thenReturn(Food.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testAddFoodWithMetrics() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenReturn(true);
		mvc.perform(post(url + "/with-metrics").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().isOk());
		mvc.perform(post(url + "/with-metrics").with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testAddFoodWithMetricsWhenError() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addWithMetrics(FoodWithMetricsInDto.builder().build())).thenThrow(DataValidationException.class);
		mvc.perform(post(url + "/with-metrics").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(FoodWithMetricsInDto.builder().build())))
				.andExpect(status().isBadRequest());
		mvc.perform(post(url + "/with-metrics").with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdateFood() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		FoodInDto inDto = FoodInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1").with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(put(url).with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put(url + "/1").with(csrf()))
				.andExpect(status().isBadRequest());
		mvc.perform(put(url).with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	private void testGetFoodById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(FoodDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	private void testDeleteFood() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}
}