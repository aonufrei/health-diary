package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/likes")
public class LikeRestController {

	private final LikeService service;

	public LikeRestController(LikeService service) {
		this.service = service;
	}

	@GetMapping
	public List<LikeDto> getAllLikes(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {
		return service.getAll(page, pageSize);
	}

	@PostMapping
	public Integer addLike(@RequestBody LikeInDto inDto) {
		return service.add(inDto).getId();
	}

	@PutMapping("/{id}")
	public boolean updateLike(@RequestBody LikeInDto inDto, @PathVariable("id") Integer id) {
		return service.update(id, inDto);
	}

	@GetMapping("/{id}")
	public LikeDto getLikeById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteLike(@PathVariable("id") Integer id) {
		service.delete(id);
		return true;
	}

}
