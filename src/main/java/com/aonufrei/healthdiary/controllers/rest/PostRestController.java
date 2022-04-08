package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Post Controller")
@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {

	private final PostService service;

	public PostRestController(PostService service) {
		this.service = service;
	}

	@Operation(summary = "Get all posts")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<PostDto> getAllPosts(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}


	@Operation(summary = "Get post by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the post you want to update")
	})
	@GetMapping("/{id}")
	public PostDto getPostById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}


	@Operation(summary = "Create post")
	@PostMapping
	public Integer addPost(@RequestBody PostInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update existing post")
	@Parameters({
			@Parameter(name = "id", description = "Id of the post you want to get")
	})
	@PutMapping("/{id}")
	public boolean updatePost(@RequestBody PostInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Delete post by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the post you want to delete")
	})
	@DeleteMapping("/{id}")
	public boolean deletePost(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

	@Operation(summary = "Get posts by person")
	@GetMapping("/person/{id}")
	@Parameters(
			{
					@Parameter(name = "id", description = "Id of the person to get feeds"),
					@Parameter(name = "page", description = "Result page number"),
					@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
			}
	)
	public List<PostDto> getPostsByPerson(@PathVariable("id") Integer personId, @RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getPostsByPerson(personId, PageRequest.of(page, Optional.ofNullable(pageSize)
				.orElse(DEFAULT_LIST_RESULT_PAGE_SIZE), Sort.by(Sort.Direction.DESC, "createdAt")));
	}

}
