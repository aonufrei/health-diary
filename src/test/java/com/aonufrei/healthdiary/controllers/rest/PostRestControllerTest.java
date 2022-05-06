package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
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
class PostRestControllerTest {

	@MockBean
	private PostService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/v1/posts";

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAllPostsForUser() throws Exception {
		testGetAllPosts();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testAddPostForUser() throws Exception {
		testAddPost();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdatePostForUser() throws Exception {
		testUpdatePost();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetPostByIdForUser() throws Exception {
		testGetPostById();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeletePostForUser() throws Exception {
		testDeletePost();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetPostsByPersonForUser() throws Exception {
		testGetPostsByPerson();
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetFeedsForPersonForUser() throws Exception {
		testGetFeedsForPerson();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAllPostsForAdmin() throws Exception {
		testGetAllPosts();
		;
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddPostForAdmin() throws Exception {
		testAddPost();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testUpdatePostForAdmin() throws Exception {
		testUpdatePost();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetPostByIdForAdmin() throws Exception {
		testGetPostById();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeletePostForAdmin() throws Exception {
		testDeletePost();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetPostsByPersonForAdmin() throws Exception {
		testGetPostsByPerson();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetFeedsForPersonForAdmin() throws Exception {
		testGetFeedsForPerson();
	}

	private void testGetAllPosts() throws Exception {
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

	private void testAddPost() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PostInDto inDto = PostInDto.builder().build();
		when(service.add(inDto)).thenReturn(Post.builder().build());
		when(service.add(null)).thenThrow(DataValidationException.class);

		mvc.perform(post(url).with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(inDto)))
				.andExpect(status().isOk());
		mvc.perform(post(url).with(csrf()))
				.andExpect(status().isBadRequest());
	}

	private void testUpdatePost() throws Exception {
		assertNotNull(service);
		assertNotNull(objectMapper);

		PostInDto inDto = PostInDto.builder().build();
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
		mvc.perform(put(url))
				.andExpect(status().is4xxClientError());
	}

	private void testGetPostById() throws Exception {
		assertNotNull(service);

		when(service.getById(anyInt())).thenReturn(PostDto.builder().build());
		mvc.perform(get(url + "/1"))
				.andExpect(status().isOk());
	}

	private void testDeletePost() throws Exception {
		assertNotNull(service);

		doNothing().when(service).delete(anyInt());
		mvc.perform(delete(url + "/1").with(csrf()))
				.andExpect(status().isOk());
	}

	private void testGetPostsByPerson() throws Exception {
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

	private void testGetFeedsForPerson() throws Exception {
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