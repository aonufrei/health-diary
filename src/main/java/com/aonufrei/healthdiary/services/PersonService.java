package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.*;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;


@Service
public class PersonService extends AbstractCrudService<Integer, Person, PersonDto, PersonInDto, PersonRepository> {

	@Autowired
	BodyReportService bodyReportService;

	public PersonService(PersonRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}


	@Transactional
	public Integer addPersonWithBodyReports(PersonWithBodyReportInDto inDto) {
		Integer personId = super.add(inDto.getPerson()).getId();

		BodyReportInDto heightReport = BodyReportInDto.builder()
				.value(inDto.getHeight())
				.type(BodyReportType.HEIGHT)
				.personId(personId)
				.loggedTime(inDto.getMentioned().atStartOfDay())
				.build();

		BodyReportInDto weightReport = BodyReportInDto.builder()
				.value(inDto.getWeight())
				.type(BodyReportType.WEIGHT)
				.personId(personId)
				.loggedTime(inDto.getMentioned().atStartOfDay())
				.build();

		bodyReportService.add(heightReport);
		bodyReportService.add(weightReport);
		return personId;
	}

	@Transactional
	public PersonWithBodyReportDto getPersonWithBodyReports(Integer personId) {
		Person person = repo.findById(personId).orElseThrow(() -> new DataValidationException("Person was not found"));
		BodyReport heightReport = bodyReportService.getRepo().getBodyReportByPersonId(person.getId(), BodyReportType.HEIGHT)
				.orElseThrow(() -> new DataValidationException("Height report was not found"));
		BodyReport weightReport = bodyReportService.getRepo().getBodyReportByPersonId(person.getId(), BodyReportType.WEIGHT)
				.orElseThrow(() -> new DataValidationException("Weight report was not found"));

		return PersonWithBodyReportDto.builder()
				.person(modelToDtoFunction.apply(person))
				.height(heightReport.getValue())
				.weight(weightReport.getValue())
				.build();

	}
}
