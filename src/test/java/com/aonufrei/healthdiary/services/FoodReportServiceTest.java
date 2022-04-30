package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.FoodReportRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodReportServiceTest {

	@Mock
	private FoodReportRepository foodReportRepository;

	@InjectMocks
	private FoodReportService foodReportService;

	private final LocalDate today = LocalDate.now();

	private final List<FoodReport> foodReportInDb = new ArrayList<FoodReport>() {{
		add(FoodReport.builder().id(1).personId(1).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(0.5f).fat(1f).build()).build());
		add(FoodReport.builder().id(2).personId(1).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(1f).fat(0.3f).build()).build());
		add(FoodReport.builder().id(3).personId(1).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(1f).calories(3f).protein(2f).fat(2f).build()).build());
		add(FoodReport.builder().id(4).personId(2).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(4f).fat(1f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(1).personId(1).reportedDate(today.plusDays(1)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(0.5f).fat(1f).build()).build());
		add(FoodReport.builder().id(2).personId(1).reportedDate(today.plusDays(1)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(1f).fat(0.3f).build()).build());
		add(FoodReport.builder().id(3).personId(1).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(1f).calories(3f).protein(2f).fat(2f).build()).build());
		add(FoodReport.builder().id(4).personId(2).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(4f).fat(1f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(1f).fat(2f).build()).build());
	}};


	@Test
	public void testGetAllFoodReportsByPersonByDayDto() {
		assertNotNull(foodReportRepository);
		assertNotNull(foodReportService);

		List<FoodReport> expectedModels = foodReportInDb.stream().filter(it -> it.getPersonId() == 1 && it.getReportedDate().equals(today)).collect(Collectors.toList());
		assertEquals(4, expectedModels.size());
		when(foodReportRepository.getFoodReportByPersonAndDay(1, today)).thenReturn(expectedModels);

		assertDoesNotThrow(() -> foodReportService.getAllFoodReportsByPersonByDayDto(1, today));
		List<FoodReportDto> actual = foodReportService.getAllFoodReportsByPersonByDayDto(1, today);
		assertEquals(expectedModels.size(), actual.size());
		assertEquals(expectedModels.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList()), actual);
		assertDoesNotThrow(() -> foodReportService.getAllFoodReportsByPersonByDayDto(100, today));
		assertEquals(Collections.emptyList(), foodReportService.getAllFoodReportsByPersonByDayDto(100, today));
	}

	@Test
	public void testGetFoodReportByPersonAndDay() {
		assertNotNull(foodReportRepository);
		assertNotNull(foodReportService);

		List<FoodReport> expectedModels = foodReportInDb.stream().filter(it -> it.getPersonId() == 1 && it.getReportedDate().equals(today)).collect(Collectors.toList());
		assertEquals(4, expectedModels.size());
		when(foodReportRepository.getFoodReportByPersonAndDay(1, today)).thenReturn(expectedModels);

		assertDoesNotThrow(() -> foodReportService.getFoodReportByPersonAndDay(1, today));
		List<FoodReport> actual = foodReportService.getFoodReportByPersonAndDay(1, today);
		assertEquals(expectedModels.size(), actual.size());
		assertEquals(expectedModels, actual);
		assertDoesNotThrow(() -> foodReportService.getFoodReportByPersonAndDay(100, today));
		assertEquals(Collections.emptyList(), foodReportService.getFoodReportByPersonAndDay(100, today));
	}

	@Test
	public void testGetFoodReportByPersonAndDateRange() {
		assertNotNull(foodReportRepository);
		assertNotNull(foodReportService);

		List<FoodReport> expectedModels = foodReportInDb.stream().filter(it -> it.getPersonId() == 1 && (it.getReportedDate().equals(today)
				|| it.getReportedDate().equals(today.plusDays(1)))).collect(Collectors.toList());
		assertEquals(6, expectedModels.size());
		when(foodReportRepository.getFoodReportByPersonAndDateRange(1, today, today.plusDays(1))).thenReturn(expectedModels);

		assertDoesNotThrow(() -> foodReportService.getFoodReportByPersonAndDateRange(1, today, today.plusDays(1)));
		List<FoodReport> actual = foodReportService.getFoodReportByPersonAndDateRange(1, today, today.plusDays(1));
		assertEquals(expectedModels.size(), actual.size());
		assertEquals(expectedModels, actual);
		assertDoesNotThrow(() -> foodReportService.getFoodReportByPersonAndDateRange(100, today, today.plusDays(1)));
		assertEquals(Collections.emptyList(), foodReportService.getFoodReportByPersonAndDateRange(100, today, today.plusDays(1)));
	}
}