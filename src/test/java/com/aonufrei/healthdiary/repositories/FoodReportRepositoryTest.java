package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FoodReportRepositoryTest {

	@Autowired
	private FoodReportRepository foodReportRepository;

	@Autowired
	private EntityManager entityManager;

	private final LocalDate today = LocalDate.now();
	private final LocalDate oneDayAgo = today.minusDays(1);
	private final LocalDate twoDaysAgo = today.minusDays(2);

	@BeforeEach
	void setUp() {
		prepareData();
	}

	@Test
	void testGetFoodReportByPersonAndDay() {
		assertNotNull(foodReportRepository);

		assertEquals(2, foodReportRepository.getFoodReportByPersonAndDay(1, today).size());
		assertTrue(foodReportRepository.getFoodReportByPersonAndDay(1, today).stream().allMatch(it -> valueIsIn(it.getId(), 1, 2)));
		assertEquals(1, foodReportRepository.getFoodReportByPersonAndDay(2, today).size());
		assertTrue(foodReportRepository.getFoodReportByPersonAndDay(2, today).stream().allMatch(it -> valueIsIn(it.getId(), 5)));
	}

	@Test
	void testGetFoodReportByPersonAndDateRange() {
		assertNotNull(foodReportRepository);

		assertEquals(4, foodReportRepository.getFoodReportByPersonAndDateRange(1, twoDaysAgo, today).size());
		assertTrue(foodReportRepository.getFoodReportByPersonAndDateRange(1, twoDaysAgo, today).stream().allMatch(it -> valueIsIn(it.getId(), 1, 2, 3, 4)));

	}

	private void prepareData() {
		assertNotNull(entityManager);

		entityManager.merge(Person.builder().id(1).name("Test 1").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());
		entityManager.merge(Person.builder().id(2).name("Test 2").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());

		entityManager.merge(Food.builder().id(1).name("Test Food 1").build());
		entityManager.merge(Food.builder().id(2).name("Test Food 2").build());
		entityManager.merge(Food.builder().id(3).name("Test Food 3").build());

		entityManager.merge(Metric.builder().id(1).foodId(1).calories(1f).fat(1f).carbs(1f).protein(1f).type(FoodMetricType.GRAM).build());
		entityManager.merge(Metric.builder().id(2).foodId(2).calories(1f).fat(1f).carbs(1f).protein(1f).type(FoodMetricType.GRAM).build());
		entityManager.merge(Metric.builder().id(3).foodId(3).calories(1f).fat(1f).carbs(1f).protein(1f).type(FoodMetricType.GRAM).build());

		entityManager.merge(FoodReport.builder().id(1).amount(100).foodId(1).personId(1).reportedDate(today).metricId(1).type(FoodReportType.BREAKFAST).build());
		entityManager.merge(FoodReport.builder().id(2).amount(100).foodId(1).personId(1).reportedDate(today).metricId(1).type(FoodReportType.DINNER).build());
		entityManager.merge(FoodReport.builder().id(3).amount(100).foodId(2).personId(1).reportedDate(oneDayAgo).metricId(2).type(FoodReportType.SNACKS).build());
		entityManager.merge(FoodReport.builder().id(4).amount(100).foodId(3).personId(1).reportedDate(twoDaysAgo).metricId(3).type(FoodReportType.BREAKFAST).build());
		entityManager.merge(FoodReport.builder().id(5).amount(100).foodId(2).personId(2).reportedDate(today).metricId(2).type(FoodReportType.DINNER).build());

		entityManager.flush();
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