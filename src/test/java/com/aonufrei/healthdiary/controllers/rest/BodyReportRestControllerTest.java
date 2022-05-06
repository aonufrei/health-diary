package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.services.BodyReportService;
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
class BodyReportRestControllerTest {

	@MockBean
	private BodyReportService bodyReportService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/body-reports";

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddBodyReportForAdmin() throws Exception {
		testGetAllBodyReports();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testUpdateBodyReportForAdmin() throws Exception {
		testAddBodyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetBodyReportByIdForAdmin() throws Exception {
		testUpdateBodyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeleteBodyReportForAdmin() throws Exception {
		testGetBodyReportById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetWeightBodyReportsByPersonForAdmin() throws Exception {
		testDeleteBodyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetHeightBodyReportsByPersonForAdmin() throws Exception {
		testGetWeightBodyReportsByPerson();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddBodyReportForUser() throws Exception {
		testGetAllBodyReports();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdateBodyReportForUser() throws Exception {
		testAddBodyReport();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetBodyReportByIdForUser() throws Exception {
		testUpdateBodyReport();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeleteBodyReportForUser() throws Exception {
		testGetBodyReportById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetWeightBodyReportsByPersonForUser() throws Exception {
		testDeleteBodyReport();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetHeightBodyReportsByPersonForUser() throws Exception {
		testGetWeightBodyReportsByPerson();
	}


	private void testGetAllBodyReports() throws Exception {
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

	private void testAddBodyReport() throws Exception {
		assertNotNull(bodyReportService);
		assertNotNull(objectMapper);

		BodyReportInDto bodyReportInDto = BodyReportInDto.builder().type(BodyReportType.HEIGHT).build();
		when(bodyReportService.add(bodyReportInDto)).thenReturn(BodyReport.builder().build());
		when(bodyReportService.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdateBodyReport() throws Exception {
		assertNotNull(bodyReportService);
		assertNotNull(objectMapper);

		BodyReportInDto bodyReportInDto = BodyReportInDto.builder().type(BodyReportType.HEIGHT).build();
		when(bodyReportService.update(1, bodyReportInDto)).thenReturn(true);
		when(bodyReportService.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().isOk());
		mvc.perform(put(url)
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(bodyReportInDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put(url + "/1").with(csrf()))
				.andExpect(status().isBadRequest());
		mvc.perform(put(url).with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	private void testGetBodyReportById() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getById(anyInt())).thenReturn(BodyReportDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	private void testDeleteBodyReport() throws Exception {
		assertNotNull(bodyReportService);

		doNothing().when(bodyReportService).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}

	private void testGetWeightBodyReportsByPerson() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getBodyReportsByPersonAndType(anyInt(), any(BodyReportType.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/weight/1"))
				.andExpect(status().isOk());
	}

	private void testGetHeightBodyReportsByPerson() throws Exception {
		assertNotNull(bodyReportService);

		when(bodyReportService.getBodyReportsByPersonAndType(anyInt(), any(BodyReportType.class)))
				.thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/height/1"))
				.andExpect(status().isOk());
	}
}