package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.FoodReportType;
import com.aonufrei.healthdiary.repositories.FoodReportRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodReportService extends AbstractCrudService<Integer, FoodReport, FoodReportDto, FoodReportInDto, FoodReportRepository> {

	public FoodReportService(FoodReportRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public List<FoodReportDto> getAllFoodReportsByPersonByDayDto(Integer personId, LocalDate reportedDate) {
		return getAllFoodReportsByPersonByDay(personId, reportedDate).stream().map(modelToDtoFunction).collect(Collectors.toList());
	}

	public List<FoodReport> getAllFoodReportsByPersonByDay(Integer personId, LocalDate reportedDate) {
		return repo.getAllByPersonIdAndReportedDate(personId, reportedDate);
	}

	public List<FoodReport> getFoodReportByPersonAndMeal(Integer personId, FoodReportType meal, LocalDate date) {
		return repo.getFoodReportByPersonAndMeal(personId, meal, date);
	}

	public List<FoodReport> getFoodReportByPersonAndMeal(Integer personId, LocalDate fromDate, LocalDate toDate) {
		return repo.getFoodReportByPersonAndMeal(personId, fromDate, toDate);
	}

}
