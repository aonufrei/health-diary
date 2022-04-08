package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.repositories.PostRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService extends AbstractCrudService<Integer, Post, PostDto, PostInDto, PostRepository> {

	public PostService(PostRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public List<PostDto> getFeedsForPerson(Integer personId, Pageable pageable) {
		return super.repo.findAll(pageable).stream().map(modelToDtoFunction).collect(Collectors.toList());
	}

	public List<PostDto> getPostsByPerson(Integer personId, Pageable pageable) {
		return super.repo.getAllByAuthorId(personId, pageable).stream().map(modelToDtoFunction).collect(Collectors.toList());
	}

}
