package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.*;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.models.Gender;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
public class PersonService extends AbstractCrudService<Integer, Person, PersonDto, PersonInDto, PersonRepository> {

	@Autowired
	BodyReportService bodyReportService;

	public PersonService(PersonRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public Integer calculateRequiredCalories(Integer personId) {
		PersonWithBodyReportDto person = getPersonWithBodyReports(personId);
		return Math.toIntExact(Math.round(calculateBMR(person) * person.getPerson().getActivity().getRatio()));
	}

	public Integer calculateRequiredCaloriesWithDeficit(Integer personId) {
		return Math.toIntExact(Math.round(calculateRequiredCalories(personId).doubleValue() * 0.7));
	}

	public Double calculateBMR(PersonWithBodyReportDto person) {
		if (person.getPerson().getGender() == Gender.FEMALE) {
			return 65.51 + (9.563 * person.getWeight()) + (1.850 * person.getHeight()) - (4.676 * getAge(person.getPerson()));
		} else {
			return 66.47 + (13.75 * person.getWeight()) + (5.003 * person.getHeight()) - (6.755 * getAge(person.getPerson()));
		}
	}

	public Integer getAge(PersonDto person) {
		return Math.toIntExact(ChronoUnit.YEARS.between(person.getDob(), LocalDate.now()));
	}

	public Integer getAge(Person person) {
		return Math.toIntExact(ChronoUnit.YEARS.between(person.getDob(), LocalDate.now()));
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
