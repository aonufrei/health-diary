package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;


@Service
public class PersonService extends AbstractCrudService<Integer, Person, PersonDto, PersonInDto, PersonRepository> {

	private final Validator validator;

	public PersonService(PersonRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		this.validator = validator;
	}

	@Override
	public boolean validateInData(PersonInDto inDto) {

		if (inDto == null) return false;

		Set<ConstraintViolation<PersonInDto>> validationErrors = validator.validate(inDto);
		validationErrors.stream().findFirst().ifPresent(error -> {
			throw new DataValidationException(error.getMessage());
		});

		return true;
	}
}
