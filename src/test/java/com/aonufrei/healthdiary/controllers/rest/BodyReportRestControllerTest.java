package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.services.BodyReportService;
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

@WebMvcTest(BodyReportRestController.class)
class BodyReportRestControllerTest {

	@MockBean
	private BodyReportService bodyReportService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/body-reports";

	@Test
	void testGetAllBodyReports() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getAll(anyInt(), anyInt())).thenReturn(Collections.emptyList());
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
	void testAddBodyReport() throws Exception {
		assertNotNull(bodyReportService);
		assertNotNull(objectMapper);

		BodyReportInDto bodyReportInDto = BodyReportInDto.builder().type(BodyReportType.HEIGHT).build();
		when(bodyReportService.add(bodyReportInDto)).thenReturn(BodyReport.builder().build());
		when(bodyReportService.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateBodyReport() throws Exception {
		assertNotNull(bodyReportService);
		assertNotNull(objectMapper);

		BodyReportInDto bodyReportInDto = BodyReportInDto.builder().type(BodyReportType.HEIGHT).build();
		when(bodyReportService.update(1, bodyReportInDto)).thenReturn(true);
		when(bodyReportService.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(put(url)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put(url + "/1"))
				.andExpect(status().isBadRequest());
		mvc.perform(put(url))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testGetBodyReportById() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getById(anyInt())).thenReturn(BodyReportDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteBodyReport() throws Exception {
		assertNotNull(bodyReportService);

		doNothing().when(bodyReportService).delete(anyInt());
		mvc.perform(delete(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetWeightBodyReportsByPerson() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getBodyReportsByPersonAndType(anyInt(), any(BodyReportType.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/weight/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetHeightBodyReportsByPerson() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getBodyReportsByPersonAndType(anyInt(), any(BodyReportType.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/height/1"))
				.andExpect(status().isOk());
	}
}