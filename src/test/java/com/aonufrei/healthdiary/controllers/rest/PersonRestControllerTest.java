package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.dtos.PersonWithBodyReportDto;
import com.aonufrei.healthdiary.dtos.PersonWithBodyReportInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.services.PersonService;
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
class PersonRestControllerTest {

	@MockBean
	private PersonService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/persons";

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAllPersonsForUser() throws Exception {
		assertNotNull(service);

		when(service.getAll(anyInt(), anyInt())).thenReturn(Collections.emptyList());
		mvc.perform(get(url).param("page", "0").param("size", "1"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddPersonForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PersonInDto inDto = PersonInDto.builder().build();
		when(service.add(inDto)).thenReturn(Person.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdatePersonForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PersonInDto inDto = PersonInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1").with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetPersonByIdForUser() throws Exception {
		testGetPersonById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeletePersonForUser() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetPersonWithBodyReportForUser() throws Exception {
		assertNotNull(service);

		when(service.getPersonWithBodyReports(anyInt())).thenReturn(PersonWithBodyReportDto.builder().build());
		mvc.perform(get(url + "/with-report/1"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddPersonWithBodyReportForUser() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addPersonWithBodyReports(PersonWithBodyReportInDto.builder().height(1f).build())).thenReturn(1);
		when(service.addPersonWithBodyReports(PersonWithBodyReportInDto.builder().height(2f).build())).thenThrow(DataValidationException.class);
		mvc.perform(post(url + "/with-report").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(PersonWithBodyReportInDto.builder().height(1f).build())))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetCaloriesRequiredForUser() throws Exception {
		testGetCaloriesRequired();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAllPersonsForAdmin() throws Exception {
		testGetAllPersons();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddPersonForAdmin() throws Exception {
		testAddPerson();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testUpdatePersonForAdmin() throws Exception {
		testUpdatePerson();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetPersonByIdForAdmin() throws Exception {
		testGetPersonById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeletePersonForAdmin() throws Exception {
		testDeletePerson();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetPersonWithBodyReportForAdmin() throws Exception {
		testGetPersonWithBodyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddPersonWithBodyReportForAdmin() throws Exception {
		testAddPersonWithBodyReport();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetCaloriesRequiredForAdmin() throws Exception {
		testGetCaloriesRequired();
	}

	private void testGetAllPersons() throws Exception {
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

	private void testAddPerson() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PersonInDto inDto = PersonInDto.builder().build();
		when(service.add(inDto)).thenReturn(Person.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdatePerson() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PersonInDto inDto = PersonInDto.builder().build();
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

	private void testGetPersonById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(PersonDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	private void testDeletePerson() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}

	private void testGetPersonWithBodyReport() throws Exception {
		assertNotNull(service);

		when(service.getPersonWithBodyReports(anyInt())).thenReturn(PersonWithBodyReportDto.builder().build());
		mvc.perform(get(url + "/with-report/1"))
				.andExpect(status().isOk());
	}

	private void testAddPersonWithBodyReport() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		when(service.addPersonWithBodyReports(PersonWithBodyReportInDto.builder().height(1f).build())).thenReturn(1);
		when(service.addPersonWithBodyReports(PersonWithBodyReportInDto.builder().height(2f).build())).thenThrow(DataValidationException.class);
		mvc.perform(post(url + "/with-report").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(PersonWithBodyReportInDto.builder().height(1f).build())))
				.andExpect(status().isOk());
		mvc.perform(post(url + "/with-report").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(PersonWithBodyReportInDto.builder().height(2f).build())))
				.andExpect(status().isBadRequest());
		mvc.perform(post(url + "/with-report").with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testGetCaloriesRequired() throws Exception {
		assertNotNull(service);

		when(service.calculateRequiredCalories(anyInt())).thenReturn(1);
		when(service.calculateRequiredCaloriesWithDeficit(anyInt())).thenReturn(1);
		mvc.perform(get(url + "/calories/1"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/calories/1").param("deficit", "true"))
				.andExpect(status().isOk());
	}
}