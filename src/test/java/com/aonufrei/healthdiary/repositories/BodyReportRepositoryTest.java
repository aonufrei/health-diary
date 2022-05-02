package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import com.aonufrei.healthdiary.models.Gender;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.models.PhysicalActivity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BodyReportRepositoryTest {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private BodyReportRepository bodyReportRepository;

	@Test
	void getBodyReportByPersonIdAndType() {
		assertNotNull(entityManager);
		assertNotNull(bodyReportRepository);

		entityManager.merge(Person.builder().id(1).name("Test 1").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());
		entityManager.merge(Person.builder().id(2).name("Test 2").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());
		entityManager.merge(Person.builder().id(3).name("Test 3").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());

		List<BodyReport> bodyReportInDb = new ArrayList<BodyReport>() {{
			add(BodyReport.builder().id(1).type(BodyReportType.HEIGHT).personId(1).value(175f).loggedTime(LocalDateTime.now()).build());
			add(BodyReport.builder().id(2).type(BodyReportType.WEIGHT).personId(1).value(80f).loggedTime(LocalDateTime.now()).build());
			add(BodyReport.builder().id(3).type(BodyReportType.HEIGHT).personId(2).value(180f).loggedTime(LocalDateTime.now()).build());
			add(BodyReport.builder().id(4).type(BodyReportType.WEIGHT).personId(2).value(100f).loggedTime(LocalDateTime.now()).build());
			add(BodyReport.builder().id(5).type(BodyReportType.HEIGHT).personId(1).value(190f).loggedTime(LocalDateTime.now()).build());
			add(BodyReport.builder().id(6).type(BodyReportType.WEIGHT).personId(3).value(90f).loggedTime(LocalDateTime.now()).build());
		}};

		for (BodyReport br : bodyReportInDb) {
			entityManager.merge(br);
		}
		entityManager.flush();

		assertEquals(2, bodyReportRepository.getBodyReportByPersonIdAndType(1, BodyReportType.HEIGHT).size());
		assertTrue(bodyReportRepository.getBodyReportByPersonIdAndType(1, BodyReportType.HEIGHT).stream().allMatch(it -> valueIsIn(it.getId(), 1, 5)));
		assertEquals(1, bodyReportRepository.getBodyReportByPersonIdAndType(1, BodyReportType.WEIGHT).size());
		assertTrue(bodyReportRepository.getBodyReportByPersonIdAndType(1, BodyReportType.WEIGHT).stream().allMatch(it -> valueIsIn(it.getId(), 2)));
	}

	private boolean valueIsIn(Integer source, int... values) {
		if (source == null) return false;
		boolean finalValue = false;
		for (int value : values) {
			finalValue |= source.equals(value);
		}
		return finalValue;
	}
}