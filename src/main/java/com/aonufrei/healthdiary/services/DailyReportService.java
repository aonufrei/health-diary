package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.CaloriesPerDay;
import com.aonufrei.healthdiary.dtos.DailyReportDto;
import com.aonufrei.healthdiary.models.FoodReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyReportService {

	private final FoodReportService foodReportService;

	public DailyReportService(FoodReportService foodReportService) {
		this.foodReportService = foodReportService;
	}

	public DailyReportDto getDailyReportCalories(Integer personId, LocalDate date) {
		List<FoodReport> allFoodForDay = foodReportService.getFoodReportByPersonAndDay(personId, date);

		float bCalories = 0f;
		float lCalories = 0f;
		float dCalories = 0f;
		float sCalories = 0f;

		float overallCarbs = 0f;
		float overallProtein = 0f;
		float overallFat = 0f;

		for (FoodReport fr : allFoodForDay) {
			int amount = Optional.ofNullable(fr.getAmount()).orElse(0);

			switch (fr.getType()) {
				case BREAKFAST:
					bCalories += amount * fr.getMetric().getCalories();
					break;
				case LUNCH:
					lCalories += amount * fr.getMetric().getCalories();
					break;
				case DINNER:
					dCalories += amount * fr.getMetric().getCalories();
					break;
				case SNACKS:
					sCalories += amount * fr.getMetric().getCalories();
					break;
			}

			overallCarbs += amount * fr.getMetric().getCarbs();
			overallProtein += amount * fr.getMetric().getProtein();
			overallFat += amount * fr.getMetric().getFat();
		}

		return DailyReportDto.builder()
				.breakfastCalories(Math.round(bCalories))
				.lunchCalories(Math.round(lCalories))
				.dinnerCalories(Math.round(dCalories))
				.snacksCalories(Math.round(sCalories))
				.fullCarbs(Math.round(overallCarbs))
				.fullProteins(Math.round(overallProtein))
				.fullFat(Math.round(overallFat))
				.build();

	}

	public List<CaloriesPerDay> getDailyReportCalories(Integer personId, LocalDate fromDate, LocalDate toDate) {
		if (fromDate.isAfter(toDate)) return Collections.emptyList();
		List<FoodReport> reportsByDays = foodReportService.getFoodReportByPersonAndDateRange(personId, fromDate, toDate);

		List<CaloriesPerDay> caloriesPerDays = new ArrayList<>();
		LocalDate temp = fromDate;
		while (temp.isBefore(toDate)) {
			LocalDate finalTemp = temp;
			List<FoodReport> foodReportForDay = reportsByDays.stream().filter(it -> it.getReportedDate().equals(finalTemp)).collect(Collectors.toList());

			Float calories = foodReportForDay.stream()
					.map(it -> it.getAmount() * it.getMetric().getCalories())
					.reduce(Float::sum)
					.orElse(0f);

			caloriesPerDays.add(CaloriesPerDay.builder().calories(Math.round(calories)).date(temp).build());
			temp = temp.plusDays(1);
		}
		caloriesPerDays.sort(Comparator.comparing(CaloriesPerDay::getDate).reversed());
		return caloriesPerDays;
	}

}
