package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class PersonService extends AbstractCrudService<Integer, Person, PersonDto, PersonInDto, PersonRepository> {

	public PersonService(PersonRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
	}

}
