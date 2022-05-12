package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.CaloriesPerDay;
import com.aonufrei.healthdiary.dtos.DailyReportDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.FoodReportType;
import com.aonufrei.healthdiary.models.Metric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyReportServiceTest {

	@Mock
	private FoodReportService foodReportService;

	@InjectMocks
	private DailyReportService dailyReportService;

	private final LocalDate today = LocalDate.now();

	private final List<FoodReport> foodReportInDb = new ArrayList<FoodReport>() {{
		add(FoodReport.builder().id(1).personId(1).reportedDate(today).foodId(1).amount(1).type(FoodReportType.BREAKFAST).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(0.5f).fat(1f).build()).build());
		add(FoodReport.builder().id(2).personId(1).reportedDate(today).foodId(1).amount(2).type(FoodReportType.DINNER).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(1f).fat(0.3f).build()).build());
		add(FoodReport.builder().id(3).personId(1).reportedDate(today).foodId(1).amount(3).type(FoodReportType.SNACKS).metric(Metric.builder().foodId(1).carbs(1f).calories(3f).protein(2f).fat(2f).build()).build());
		add(FoodReport.builder().id(4).personId(2).reportedDate(today).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(4f).fat(1f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today).foodId(1).amount(4).type(FoodReportType.LUNCH).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(1).personId(1).reportedDate(today.plusDays(1)).foodId(2).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(0.5f).fat(1f).build()).build());
		add(FoodReport.builder().id(2).personId(1).reportedDate(today.plusDays(1)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(1f).fat(0.3f).build()).build());
		add(FoodReport.builder().id(3).personId(1).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(1f).calories(3f).protein(2f).fat(2f).build()).build());
		add(FoodReport.builder().id(4).personId(2).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(2f).calories(1f).protein(4f).fat(1f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(2)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(3)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(2f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(4)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(3f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(5)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(4f).protein(1f).fat(2f).build()).build());
		add(FoodReport.builder().id(5).personId(1).reportedDate(today.plusDays(6)).foodId(1).amount(1).metric(Metric.builder().foodId(1).carbs(3f).calories(5f).protein(1f).fat(2f).build()).build());
	}};

	@Test
	void testGetDailyReportCalories() {
		assertNotNull(foodReportService);
		assertNotNull(dailyReportService);

		Integer personId = 1;

		DailyReportDto dailyReportDto = new DailyReportDto();
		dailyReportDto.setBreakfastCalories(Math.round(1 * 2f));
		dailyReportDto.setDinnerCalories(Math.round(2 * 1f));
		dailyReportDto.setLunchCalories(Math.round(4 * 2f));
		dailyReportDto.setSnacksCalories(Math.round(3 * 3f));
		dailyReportDto.setFullCarbs(Math.round(1 * 3f + 2 * 2f + 3 * 1f + 4 * 3f));
		dailyReportDto.setFullProteins(Math.round(1 * 0.5f + 2 * 1f + 3 * 2f + 4 * 1f));
		dailyReportDto.setFullFat(Math.round(1 * 1f + 2 * 0.3f + 3 * 2f + 4 * 2f));

		List<FoodReport> expectedList = foodReportInDb.stream()
				.filter(it -> it.getPersonId().equals(personId) && it.getReportedDate() == today).collect(Collectors.toList());
		when(foodReportService.getFoodReportByPersonAndDay(anyInt(), any(LocalDate.class))).thenReturn(expectedList);

		assertDoesNotThrow(() -> dailyReportService.getDailyReportCalories(personId, today));
		assertEquals(dailyReportDto, dailyReportService.getDailyReportCalories(personId, today));
		assertEquals(dailyReportDto, dailyReportService.getDailyReportCalories(personId, today));
	}

	@Test
	void testGetDailyReportCaloriesByDateRange() {
		assertNotNull(foodReportService);
		assertNotNull(dailyReportService);

		List<CaloriesPerDay> expectedResult = new ArrayList<>();
		expectedResult.add(new CaloriesPerDay(today.plusDays(3), 2));
		expectedResult.add(new CaloriesPerDay(today.plusDays(4), 3));
		expectedResult.add(new CaloriesPerDay(today.plusDays(5), 4));
		List<FoodReport> expectedList = foodReportInDb.stream()
				.filter(it -> it.getReportedDate().isAfter(today.plusDays(2))).collect(Collectors.toList());
		when(foodReportService.getFoodReportByPersonAndDateRange(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedList);

		assertDoesNotThrow(() -> dailyReportService.getDailyReportCalories(1, today.plusDays(3), today.plusDays(6)));
		assertEquals(Collections.emptyList(), dailyReportService.getDailyReportCalories(1, today.plusDays(1), today));
		assertEquals(3, dailyReportService.getDailyReportCalories(1, today.plusDays(3), today.plusDays(6)).size());
		assertEquals(new HashSet<>(expectedResult), new HashSet<>(dailyReportService.getDailyReportCalories(1, today.plusDays(3), today.plusDays(6))));
	}
}