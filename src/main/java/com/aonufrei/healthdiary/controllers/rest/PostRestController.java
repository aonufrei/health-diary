package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {

	private final PostService service;

	public PostRestController(PostService service) {
		this.service = service;
	}

	@GetMapping
	public List<PostDto> getAllPosts(@RequestParam("page") Integer page, @RequestParam("page_size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@GetMapping("/{id}")
	public PostDto getPostById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@PostMapping
	public Integer addPost(@RequestBody PostInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updatePost(@RequestBody PostInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@DeleteMapping("/{id}")
	public boolean deletePost(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
