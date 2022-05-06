package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Like;
import com.aonufrei.healthdiary.services.LikeService;
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
class LikeRestControllerTest {

	@MockBean
	private LikeService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/likes";

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetAllLikesForAdmin() throws Exception {
		testGetAllLikes();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testAddLikeForAdmin() throws Exception {
		testAddLike();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testUpdateLikeForAdmin() throws Exception {
		testUpdateLike();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testGetLikeByIdForAdmin() throws Exception {
		testGetLikeById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteLikeForAdmin() throws Exception {
		testDeleteLike();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void testDeleteLikeByPersonAndPostForAdmin() throws Exception {
		testDeleteLikeByPersonAndPost();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetAllLikesForUser() throws Exception {
		testGetAllLikes();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testAddLikeForUser() throws Exception {
		testAddLike();
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testUpdateLikeForUser() throws Exception {
				assertNotNull(service);
		assertNotNull(objectMapper);

		LikeInDto inDto = LikeInDto.builder().build();
		when(service.update(1, inDto)).thenReturn(true);
		when(service.update(1, null)).thenThrow(DataValidationException.class);

		mvc.perform(put(url + "/1").with(csrf())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetLikeByIdForUser() throws Exception {
				assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(LikeDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testDeleteLikeForUser() throws Exception {
				assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testDeleteLikeByPersonAndPostForUser() throws Exception {
		testDeleteLikeByPersonAndPost();
	}

	private void testGetAllLikes() throws Exception {
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

	private void testAddLike() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		LikeInDto inDto = LikeInDto.builder().build();
		when(service.add(inDto)).thenReturn(Like.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdateLike() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		LikeInDto inDto = LikeInDto.builder().build();
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

	private void testGetLikeById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(LikeDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	private void testDeleteLike() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}

	private void testDeleteLikeByPersonAndPost() throws Exception {
		assertNotNull(service);

		when(service.deleteLikeByPersonAndPost(anyInt(), anyInt())).thenReturn(true);
		mvc.perform(delete(url).with(csrf()).param("person_id", "1").param("post_id", "1"))
				.andExpect(status().isOk());
		mvc.perform(delete(url).with(csrf()).param("post_id", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(delete(url).with(csrf()).param("person_id", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(delete(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}
}