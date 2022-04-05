package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.repositories.FoodReportRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;

@Service
public class FoodReportService extends AbstractCrudService<Integer, FoodReport, FoodReportDto, FoodReportInDto, FoodReportRepository> {

	public FoodReportService(FoodReportRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
	}

}
