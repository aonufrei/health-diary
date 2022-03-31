package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.models.Like;
import com.aonufrei.healthdiary.repositories.LikeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

	private final LikeRepository repo;

	public LikeService(LikeRepository repo) {
		this.repo = repo;
	}

	public List<LikeDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(LikeDto::new).collect(Collectors.toList());
	}

	public LikeDto getById(Integer id) {
		if (id == null) {
			return null;
		}
		Like like = repo.getById(id);
		if (like == null) {
			return null;
		}

		return new LikeDto(like);
	}

	public Integer add(LikeInDto inDto) {
		if (inDto == null || inDto.getAuthorId() == null || inDto.getPostId() == null) {
			return null;
		}

		return repo.likePost(inDto.getAuthorId(), inDto.getPostId());
	}

	@Transactional
	public boolean update(Integer id, LikeInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}

		if (!repo.existsById(id)) {
			return false;
		}

		if (inDto.getAuthorId() != null && inDto.getPostId() != null) {
			repo.updateLikeAuthorAndPost(inDto.getAuthorId(), inDto.getPostId(), id);
		} else if (inDto.getAuthorId() != null && inDto.getPostId() == null) {
			repo.updateLikeAuthor(inDto.getAuthorId(), id);
		} else if (inDto.getAuthorId() == null && inDto.getPostId() != null) {
			repo.updateLikePost(inDto.getPostId(), id);
		}

		return true;
	}

	public boolean delete(Integer id) {
		repo.deleteById(id);
		return true;
	}
}
