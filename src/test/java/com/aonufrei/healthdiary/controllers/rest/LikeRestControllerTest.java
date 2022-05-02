package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Like;
import com.aonufrei.healthdiary.services.LikeService;
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

@WebMvcTest(LikeRestController.class)
class LikeRestControllerTest {

	@MockBean
	private LikeService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/likes";

	@Test
	void testGetAllLikes() throws Exception {
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
	void testAddLike() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		LikeInDto inDto = LikeInDto.builder().build();
		when(service.add(inDto)).thenReturn(Like.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateLike() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		LikeInDto inDto = LikeInDto.builder().build();
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
	void testGetLikeById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(LikeDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteLike() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteLikeByPersonAndPost() throws Exception {
		assertNotNull(service);

		when(service.deleteLikeByPersonAndPost(anyInt(), anyInt())).thenReturn(true);
		mvc.perform(delete(url).param("person_id", "1").param("post_id", "1"))
				.andExpect(status().isOk());
		mvc.perform(delete(url).param("post_id", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(delete(url).param("person_id", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(delete(url))
				.andExpect(status().isBadRequest());
	}
}