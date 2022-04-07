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

@Service
public class FoodReportService extends AbstractCrudService<Integer, FoodReport, FoodReportDto, FoodReportInDto, FoodReportRepository> {

	public FoodReportService(FoodReportRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public List<FoodReport> getFoodReportByPersonAndMeal(Integer personId, FoodReportType meal, LocalDate date) {
		return repo.getFoodReportByPersonAndMeal(personId, meal, date);
	}

}
