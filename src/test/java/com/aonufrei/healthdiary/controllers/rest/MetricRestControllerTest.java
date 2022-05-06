package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.services.MetricService;
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
class MetricRestControllerTest {

	@MockBean
	private MetricService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/metrics";


	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAllMetricsForUser() throws Exception {
		testGetAllMetrics();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddMetricForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		MetricInDto inDto = MetricInDto.builder().build();
		when(service.add(inDto)).thenReturn(Metric.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdateMetricForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		MetricInDto inDto = MetricInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1").with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetMetricByIdForUser() throws Exception {
		testGetMetricById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeleteMetricForUser() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAllMetricsForAdmin() throws Exception {
		testGetAllMetrics();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddMetricForAdmin() throws Exception {
		testAddMetric();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testUpdateMetricForAdmin() throws Exception {
		testUpdateMetric();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetMetricByIdForAdmin() throws Exception {
		testGetMetricById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeleteMetricForAdmin() throws Exception {
		testDeleteMetric();
	}

	private void testGetAllMetrics() throws Exception {
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


	private void testAddMetric() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		MetricInDto inDto = MetricInDto.builder().build();
		when(service.add(inDto)).thenReturn(Metric.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdateMetric() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		MetricInDto inDto = MetricInDto.builder().build();
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


	private void testGetMetricById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(MetricDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}


	private void testDeleteMetric() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}
}