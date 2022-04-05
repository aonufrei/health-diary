package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.repositories.AimRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class AimService extends AbstractCrudService<Integer, Aim, AimDto, AimInDto, AimRepository> {

	public AimService(AimRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
	}

}
