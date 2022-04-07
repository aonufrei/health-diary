package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.CaloriesPerDay;
import com.aonufrei.healthdiary.dtos.DailyReportDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.FoodReportType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DailyReportService {

	private final FoodReportService foodReportService;

	public DailyReportService(FoodReportService foodReportService) {
		this.foodReportService = foodReportService;
	}

	@Transactional
	public DailyReportDto getDailyReportCalories(Integer personId, LocalDate date) {
		List<FoodReport> breakfastFood = foodReportService.getFoodReportByPersonAndMeal(personId, FoodReportType.BREAKFAST, date);
		List<FoodReport> lunchFood = foodReportService.getFoodReportByPersonAndMeal(personId, FoodReportType.LUNCH, date);
		List<FoodReport> dinnerFood = foodReportService.getFoodReportByPersonAndMeal(personId, FoodReportType.DINNER, date);
		List<FoodReport> snacksFood = foodReportService.getFoodReportByPersonAndMeal(personId, FoodReportType.SNACKS, date);

		Float bCalories = breakfastFood.stream().map(it -> it.getAmount() * it.getMetric().getCalories()).reduce(Float::sum).orElse(0f);
		Float lCalories = lunchFood.stream().map(it -> it.getAmount() * it.getMetric().getCalories()).reduce(Float::sum).orElse(0f);
		Float dCalories = dinnerFood.stream().map(it -> it.getAmount() * it.getMetric().getCalories()).reduce(Float::sum).orElse(0f);
		Float sCalories = snacksFood.stream().map(it -> it.getAmount() * it.getMetric().getCalories()).reduce(Float::sum).orElse(0f);

		float overallCarbs = 0f;
		float overallProtein = 0f;
		float overallFat = 0f;
		List<FoodReport> reports = Stream.of(breakfastFood, lunchFood, dinnerFood, snacksFood)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());

		for (FoodReport fr : reports) {
			overallCarbs += fr.getAmount() * fr.getMetric().getCarbs();
			overallProtein += fr.getAmount() * fr.getMetric().getProtein();
			overallFat += fr.getAmount() * fr.getMetric().getFat();
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
		List<FoodReport> reportsByDays = foodReportService.getFoodReportByPersonAndMeal(personId, fromDate, toDate);

		List<CaloriesPerDay> caloriesPerDays = new ArrayList<>();
		for (LocalDate temp = fromDate; temp.isBefore(toDate); temp = temp.plusDays(1)) {
			LocalDate finalTemp = temp;
			List<FoodReport> foodReportForDay = reportsByDays.stream().filter(it -> it.getReportedDate().equals(finalTemp)).collect(Collectors.toList());

			Float calories = foodReportForDay.stream()
					.map(it -> it.getAmount() * it.getMetric().getCalories())
					.reduce(Float::sum)
					.orElse(0f);

			caloriesPerDays.add(CaloriesPerDay.builder().calories(Math.round(calories)).date(temp).build());
		}
		caloriesPerDays.sort(Comparator.comparing(CaloriesPerDay::getDate).reversed());
		return caloriesPerDays;
	}

}
