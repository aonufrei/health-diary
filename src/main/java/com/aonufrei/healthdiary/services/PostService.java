package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PostDto;
import com.aonufrei.healthdiary.dtos.PostInDto;
import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.repositories.PostRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class PostService extends AbstractCrudService<Integer, Post, PostDto, PostInDto, PostRepository> {

	public PostService(PostRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

}
