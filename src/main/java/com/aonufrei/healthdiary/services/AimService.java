package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.repositories.AimRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AimService extends AbstractCrudService<Integer, Aim, AimDto, AimInDto, AimRepository> {

	public AimService(AimRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public List<AimDto> getAllByPersonId(Integer personId, Integer pageSize, Integer pageNumber) {
		return repo.getAllByPersonId(Pageable.ofSize(pageSize).withPage(pageNumber), personId).stream()
				.map(ModelDtoUtil::modelToDto).collect(Collectors.toList());
	}

}
