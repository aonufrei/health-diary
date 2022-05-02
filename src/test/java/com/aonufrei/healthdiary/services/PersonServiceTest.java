package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.*;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.*;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.*;

import static org.hibernate.validator.internal.engine.ConstraintViolationImpl.forBeanValidation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	private BodyReportService bodyReportService;

	@Mock
	private PersonRepository personRepository;

	@Spy
	@InjectMocks
	private PersonService personService;

	@Test
	void testCalculateRequiredCalories() {
		assertNotNull(personService);

		PersonWithBodyReportDto testPerson0 = createTestPerson(PhysicalActivity.ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson1 = createTestPerson(PhysicalActivity.LIGHTLY_ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson2 = createTestPerson(PhysicalActivity.MODERATELY_ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson3 = createTestPerson(PhysicalActivity.SEDENTARY, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson4 = createTestPerson(PhysicalActivity.VERY_ACTIVE, Gender.MALE, 20, 175, 90);

		doReturn(testPerson0).when(personService).getPersonWithBodyReports(0);
		doReturn(testPerson1).when(personService).getPersonWithBodyReports(1);
		doReturn(testPerson2).when(personService).getPersonWithBodyReports(2);
		doReturn(testPerson3).when(personService).getPersonWithBodyReports(3);
		doReturn(testPerson4).when(personService).getPersonWithBodyReports(4);

		List<PersonWithBodyReportDto> testPeople = new ArrayList<>();
		testPeople.add(testPerson0);
		testPeople.add(testPerson1);
		testPeople.add(testPerson2);
		testPeople.add(testPerson3);
		testPeople.add(testPerson4);

		for (int i = 0; i < 5; i++) {
			int finalId = i;
			assertDoesNotThrow(() -> personService.calculateRequiredCalories(finalId));
			assertEquals(Math.toIntExact(Math.round(personService.calculateBMR(testPeople.get(i))
					* testPeople.get(i).getPerson().getActivity().getRatio())), personService.calculateRequiredCalories(finalId));
		}
	}

	@Test
	void testCalculateRequiredCaloriesWithDeficit() {
		assertNotNull(personService);

		PersonWithBodyReportDto testPerson0 = createTestPerson(PhysicalActivity.ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson1 = createTestPerson(PhysicalActivity.LIGHTLY_ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson2 = createTestPerson(PhysicalActivity.MODERATELY_ACTIVE, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson3 = createTestPerson(PhysicalActivity.SEDENTARY, Gender.MALE, 20, 175, 90);
		PersonWithBodyReportDto testPerson4 = createTestPerson(PhysicalActivity.VERY_ACTIVE, Gender.MALE, 20, 175, 90);

		doReturn(testPerson0).when(personService).getPersonWithBodyReports(0);
		doReturn(testPerson1).when(personService).getPersonWithBodyReports(1);
		doReturn(testPerson2).when(personService).getPersonWithBodyReports(2);
		doReturn(testPerson3).when(personService).getPersonWithBodyReports(3);
		doReturn(testPerson4).when(personService).getPersonWithBodyReports(4);

		for (int i = 0; i < 5; i++) {
			int finalId = i;
			assertDoesNotThrow(() -> personService.calculateRequiredCaloriesWithDeficit(finalId));
			assertEquals(Math.toIntExact(Math.round(personService.calculateRequiredCalories(finalId) * 0.7)),
					personService.calculateRequiredCaloriesWithDeficit(finalId));
		}
	}

	@Test
	void testCalculateBMR() {
		assertNotNull(personService);

		assertEquals(Math.round(1156.41), Math.round(personService.calculateBMR(createTestPerson(Gender.FEMALE, 20, 175, 90))));
		assertEquals(Math.round(1222.8269999999998), Math.round(personService.calculateBMR(createTestPerson(Gender.FEMALE, 18, 180, 95))));
		assertEquals(Math.round(1305.79), Math.round(personService.calculateBMR(createTestPerson(Gender.FEMALE, 25, 165, 110))));
		assertEquals(Math.round(2044.395), Math.round(personService.calculateBMR(createTestPerson(Gender.MALE, 20, 175, 90))));
		assertEquals(Math.round(2151.67), Math.round(personService.calculateBMR(createTestPerson(Gender.MALE, 18, 180, 95))));
		assertEquals(Math.round(2235.59), Math.round(personService.calculateBMR(createTestPerson(Gender.MALE, 25, 165, 110))));

		PersonWithBodyReportDto testPerson = new PersonWithBodyReportDto();
		testPerson.setPerson(PersonDto.builder().gender(null).dob(LocalDate.now().minusYears(1)).build());
		testPerson.setHeight(null);
		testPerson.setWeight(null);
		assertThrows(NullPointerException.class, () -> personService.calculateBMR(testPerson));
	}

	private PersonWithBodyReportDto createTestPerson(Gender gender, int age, float height, float weight) {
		return createTestPerson(null, gender, age, height, weight);
	}

	private PersonWithBodyReportDto createTestPerson(PhysicalActivity activity, Gender gender, int age, float height, float weight) {
		PersonWithBodyReportDto testPerson = new PersonWithBodyReportDto();
		testPerson.setPerson(PersonDto.builder().gender(gender).activity(activity).dob(LocalDate.now().minusYears(age)).build());
		testPerson.setHeight(height);
		testPerson.setWeight(weight);
		return testPerson;
	}

	@Test
	void testGetAge() {
		PersonDto personDto = PersonDto.builder().name("test").dob(LocalDate.now().minusYears(20)).build();
		assertEquals(20, personService.getAge(personDto));
		assertThrows(NullPointerException.class, () -> personService.getAge(null));
	}

	@Test
	void testAddPersonWithBodyReports() {
		assertNotNull(bodyReportService);
		assertNotNull(personService);

		PersonWithBodyReportInDto personWithBodyReport = PersonWithBodyReportInDto.builder()
				.person(PersonInDto.builder().name("Test").build())
				.weight(90f)
				.height(175f)
				.build();

		when(personService.add(any(PersonInDto.class))).thenReturn(Person.builder().id(1).build());
		when(bodyReportService.add(any(BodyReportInDto.class))).thenReturn(BodyReport.builder().id(1).build());

		assertDoesNotThrow(() -> personService.addPersonWithBodyReports(personWithBodyReport));
		personWithBodyReport.setWeight(10f);
		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
		personWithBodyReport.setWeight(1000f);
		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
		personWithBodyReport.setWeight(90f);
		personWithBodyReport.setHeight(10f);
		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
		personWithBodyReport.setHeight(1000f);
		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
		assertNull(personService.addPersonWithBodyReports(null));
	}

	@Test
	void testAddPersonWithBodyReportsWhenInvalidInDto() {
		assertNotNull(bodyReportService);
		assertNotNull(personService);

		PersonWithBodyReportInDto personWithBodyReport = PersonWithBodyReportInDto.builder()
				.person(PersonInDto.builder().name("Test").build())
				.weight(90f)
				.height(175f)
				.build();

		personService.setValidator(mock(Validator.class));
		when(personService.getValidator().validate(any(PersonWithBodyReportInDto.class))).thenReturn(createFakeValidationError(PersonWithBodyReportInDto.class));

		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
	}

	@Test
	void testAddPersonWithBodyReportsWhenInvalidPerson() {
		assertNotNull(bodyReportService);
		assertNotNull(personService);

		PersonWithBodyReportInDto personWithBodyReport = PersonWithBodyReportInDto.builder()
				.person(PersonInDto.builder().name("Test").build())
				.weight(90f)
				.height(175f)
				.build();

		personService.setValidator(mock(Validator.class));
		when(personService.getValidator().validate(personWithBodyReport)).thenReturn(new HashSet<>());
		when(personService.getValidator().validate(personWithBodyReport.getPerson())).thenReturn(createFakeValidationError(PersonInDto.class));

		assertThrows(DataValidationException.class, () -> personService.addPersonWithBodyReports(personWithBodyReport));
	}

	@Test
	void testGetPersonWithBodyReports() {
		assertNotNull(bodyReportService);
		assertNotNull(personRepository);
		assertNotNull(personService);

		LocalDate today = LocalDate.now();

		List<BodyReportDto> heightsBodyReports = new ArrayList<>();
		heightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.atStartOfDay()).build());
		heightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.minusDays(1).atStartOfDay()).build());
		heightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.minusDays(2).atStartOfDay()).build());
		List<BodyReportDto> weightsBodyReports = new ArrayList<>();
		weightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.atStartOfDay()).build());
		weightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.minusDays(1).atStartOfDay()).build());
		weightsBodyReports.add(BodyReportDto.builder().id(1).loggedTime(today.minusDays(2).atStartOfDay()).build());

		when(bodyReportService.getBodyReportsByPersonAndType(1, BodyReportType.HEIGHT)).thenReturn(heightsBodyReports);
		when(bodyReportService.getBodyReportsByPersonAndType(1, BodyReportType.WEIGHT)).thenReturn(weightsBodyReports);
		when(bodyReportService.getBodyReportsByPersonAndType(100, BodyReportType.HEIGHT)).thenReturn(Collections.emptyList());
		when(bodyReportService.getBodyReportsByPersonAndType(101, BodyReportType.HEIGHT)).thenReturn(heightsBodyReports);
		when(bodyReportService.getBodyReportsByPersonAndType(101, BodyReportType.WEIGHT)).thenReturn(Collections.emptyList());

		when(personRepository.findById(1)).thenReturn(Optional.ofNullable(Person.builder().id(1).build()));
		when(personRepository.findById(100)).thenReturn(Optional.ofNullable(Person.builder().id(100).build()));
		when(personRepository.findById(101)).thenReturn(Optional.ofNullable(Person.builder().id(101).build()));
		when(personRepository.findById(102)).thenReturn(Optional.empty());

		assertDoesNotThrow(() -> personService.getPersonWithBodyReports(1));
		assertNotNull(personService.getPersonWithBodyReports(1));
		assertThrows(DataValidationException.class, () -> personService.getPersonWithBodyReports(100));
		assertThrows(DataValidationException.class, () -> personService.getPersonWithBodyReports(101));
		assertThrows(DataValidationException.class, () -> personService.getPersonWithBodyReports(102));
	}

	private <T> Set<ConstraintViolation<T>> createFakeValidationError(Class<T> ignore) {
		Set<ConstraintViolation<T>> expectedValidationErrors = new HashSet<>();
		ConstraintViolation<T> violation = forBeanValidation(null, null, null, null, null, null, null, null, null, null, null);
		expectedValidationErrors.add(violation);
		return expectedValidationErrors;
	}
}