package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.services.AimService;
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

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AimRestControllerTest {

	@MockBean
	private AimService aimService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAllAimsForAdmin() throws Exception {
		testGetAllAims();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddAimWhenValidAimInDtoForAdmin() throws Exception {
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testUpdateAimForAdmin() throws Exception {
		testUpdateAim();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAimByIdForAdmin() throws Exception {
		testGetAimById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAimsByPersonIdForAdmin() throws Exception {
		testGetAimsByPersonId();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeleteAimForAdmin() throws Exception {
		testDeleteAim();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAllAimsForUser() throws Exception {
		testGetAllAims();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddAimWhenValidAimInDtoForUser() throws Exception {
		testAddAimWhenValidAimInDto();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdateAimForUser() throws Exception {
		testUpdateAim();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAimByIdForUser() throws Exception {
		testGetAimById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAimsByPersonIdForUser() throws Exception {
		testGetAimsByPersonId();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeleteAimForUser() throws Exception {
		testDeleteAim();
	}

	private void testGetAllAims() throws Exception {
		assertNotNull(aimService);

		when(aimService.getAll(anyInt(), anyInt())).thenReturn(Collections.emptyList());
		mvc.perform(get("/api/v1/aims").param("person_id", "0").param("page", "0")
						.param("size", "0"))
				.andExpect(status().isOk());
		mvc.perform(get("/api/v1/aims").param("page", "0"))
				.andExpect(status().isOk());
		mvc.perform(get("/api/v1/aims"))
				.andExpect(status().isBadRequest());
	}

	private void testAddAimWhenValidAimInDto() throws Exception {
		assertNotNull(aimService);
		assertNotNull(objectMapper);

		AimInDto aimInDto = AimInDto.builder().personId(1).mentioned(LocalDate.now()).status(AimStatus.CREATED).build();
		when(aimService.add(aimInDto)).thenReturn(Aim.builder().build());
		when(aimService.add(null)).thenThrow(DataValidationException.class);
		mvc.perform(post("/api/v1/aims").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(aimInDto)))
				.andExpect(status().isOk());
		mvc.perform(post("/api/v1/aims").with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdateAim() throws Exception {
		assertNotNull(aimService);

		AimInDto aimInDto = AimInDto.builder().personId(1).mentioned(LocalDate.now()).status(AimStatus.CREATED).build();
		when(aimService.update(1, aimInDto)).thenReturn(true);
		when(aimService.update(1, null)).thenThrow(DataValidationException.class);
		mvc.perform(put("/api/v1/aims/1")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(aimInDto)))
				.andExpect(status().isOk());
		mvc.perform(put("/api/v1/aims")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(aimInDto)))
				.andExpect(status().is4xxClientError());
		mvc.perform(put("/api/v1/aims/1").with(csrf()))
				.andExpect(status().isBadRequest());
		mvc.perform(put("/api/v1/aims").with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	private void testGetAimById() throws Exception {
		assertNotNull(aimService);

		when(aimService.getById(anyInt())).thenReturn(AimDto.builder().build());
		mvc.perform(get("/api/v1/aims/1"))
				.andExpect(status().isOk());
	}

	private void testGetAimsByPersonId() throws Exception {
		assertNotNull(aimService);

		when(aimService.getAllByPersonId(anyInt(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
		mvc.perform(get("/api/v1/aims/person/1"))
				.andExpect(status().isBadRequest());
		mvc.perform(get("/api/v1/aims/person/1").param("page", "0").param("size", "0"))
				.andExpect(status().isOk());
		mvc.perform(get("/api/v1/aims/person/1").param("page", "0"))
				.andExpect(status().isOk());
		mvc.perform(get("/api/v1/aims/person/1").param("size", "0"))
				.andExpect(status().isBadRequest());
	}

	private void testDeleteAim() throws Exception {
		assertNotNull(aimService);

		doNothing().when(aimService).delete(anyInt());
		mvc.perform(delete("/api/v1/aims/1").with(csrf()))
				.andExpect(status().isOk());
	}
}