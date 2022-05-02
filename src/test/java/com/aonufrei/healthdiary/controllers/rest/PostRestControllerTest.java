package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {

	@MockBean
	private PostService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/posts";

	@Test
	void testGetAllPosts() throws Exception {
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
	void testAddPost() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PostInDto inDto = PostInDto.builder().build();
		when(service.add(inDto)).thenReturn(Post.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdatePost() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PostInDto inDto = PostInDto.builder().build();
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
	void testGetPostById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(PostDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testDeletePost() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetPostsByPerson() throws Exception {
		assertNotNull(service);

		when(service.getPostsByPerson(anyInt(), any(Pageable.class))).thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/person/1").param("page", "1").param("size", "1"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/person/1").param("page", "1"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/person/1").param("size", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/person/1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetFeedsForPerson() throws Exception {
		assertNotNull(service);

		when(service.getFeedsForPerson(anyInt(), any(Pageable.class))).thenReturn(Collections.emptyList());
		mvc.perform(get(url + "/feeds/1").param("page", "1").param("size", "1"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/feeds/1").param("page", "1"))
				.andExpect(status().isOk());
		mvc.perform(get(url + "/feeds/1").param("size", "1"))
				.andExpect(status().isBadRequest());
		mvc.perform(get(url + "/feeds/1"))
				.andExpect(status().isBadRequest());
	}
}