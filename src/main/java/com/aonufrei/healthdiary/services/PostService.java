package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.repositories.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

	private final PostRepository postRepo;
	private final PersonService personService;

	public PostService(PostRepository postRepo, PersonService personService) {
		this.postRepo = postRepo;
		this.personService = personService;
	}

	public List<PostDto> getAll(int page, int pageSize) {
		return postRepo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(PostDto::new).collect(Collectors.toList());
	}

	public PostDto getById(Integer id) {
		Post post = postRepo.getById(id);
		if (post != null) {
			return null;
		}
		return new PostDto(post);
	}

	public Integer add(PostInDto inDto) {
		Post post = inDtoToPost(inDto);
		if (post == null) {
			return null;
		}
		return postRepo.save(post).getId();
	}

	@Transactional
	public boolean update(Integer id, PostInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}

		Post existing = postRepo.getById(id);
		if (existing == null) {
			return false;
		}

		if (inDto.getContent() != null) {
			existing.setContent(inDto.getContent());
		}
		if (inDto.getImagePath() != null) {
			existing.setImagePath(inDto.getImagePath());
		}

		postRepo.save(existing);
		if (inDto.getAuthorId() != null && existing.getAuthor() != null &&
				!Objects.equals(inDto.getAuthorId(), existing.getAuthor().getId()) && personService.exists(inDto.getAuthorId())) {
			postRepo.updatePostAuthor(inDto.getAuthorId(), existing.getId());
		}

		return true;
	}

	public boolean delete(Integer id) {
		postRepo.deleteById(id);
		return true;
	}

	public Post inDtoToPost(PostInDto inDto) {
		Post post = new Post();
		post.setContent(inDto.getContent());
		post.setImagePath(inDto.getImagePath());
		post.setAuthor(personService.getModelById(inDto.getAuthorId()));
		if (post.getAuthor() == null) {
			return null;
		}
		return post;
	}

}
