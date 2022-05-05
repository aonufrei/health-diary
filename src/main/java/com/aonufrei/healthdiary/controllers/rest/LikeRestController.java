package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.services.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aonufrei.healthdiary.configurations.ApplicationConfigs.DEFAULT_LIST_RESULT_PAGE_SIZE;

@Tag(name = "Like Controller")
@RestController
@RequestMapping("api/v1/likes")
@SecurityRequirement(name = "app-security")
public class LikeRestController {

	private final LikeService service;

	public LikeRestController(LikeService service) {
		this.service = service;
	}

	@Operation(summary = "Get all likes")
	@Parameters({
			@Parameter(name = "page", description = "Result page number"),
			@Parameter(name = "size", description = "The size of the requested page. Default value is 10")
	})
	@GetMapping
	public List<LikeDto> getAllLikes(@RequestParam("page") Integer page, @RequestParam(value = "size", required = false) Integer pageSize) {
		return service.getAll(page, Optional.ofNullable(pageSize).orElse(DEFAULT_LIST_RESULT_PAGE_SIZE));
	}

	@Operation(summary = "Create like")
	@PostMapping
	public Integer addLike(@RequestBody LikeInDto inDto) {
		return service.add(inDto).getId();
	}

	@Operation(summary = "Update like")
	@Parameters({
			@Parameter(name = "id", description = "Id of the like you want to update")
	})
	@PutMapping("/{id}")
	public boolean updateLike(@RequestBody LikeInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@Operation(summary = "Get like by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the like you want to get")
	})
	@GetMapping("/{id}")
	public LikeDto getLikeById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@Operation(summary = "Delete like by id")
	@Parameters({
			@Parameter(name = "id", description = "Id of the like you want to delete")
	})
	@DeleteMapping("/{id}")
	public boolean deleteLike(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

	@Operation(summary = "Delete like by person id and post id")
	@Parameters({
			@Parameter(name = "person_id", description = "Id of the person"),
			@Parameter(name = "post_id", description = "Id of the post")
	})
	@DeleteMapping
	public boolean deleteLike(@RequestParam("person_id") Integer personId, @RequestParam("post_id") Integer postId) {
		service.deleteLikeByPersonAndPost(personId, postId);
		return true;
	}

}
