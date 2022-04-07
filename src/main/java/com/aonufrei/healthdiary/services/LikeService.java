package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.LikeDto;
import com.aonufrei.healthdiary.dtos.LikeInDto;
import com.aonufrei.healthdiary.models.Like;
import com.aonufrei.healthdiary.repositories.LikeRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class LikeService extends AbstractCrudService<Integer, Like, LikeDto, LikeInDto, LikeRepository> {

	public LikeService(LikeRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

}
