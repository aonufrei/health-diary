package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.repositories.BodyReportRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;

import javax.validation.Validator;


@Service
public class BodyReportService extends AbstractCrudService<Integer, BodyReport, BodyReportDto, BodyReportInDto, BodyReportRepository> {

	public BodyReportService(BodyReportRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}
}
