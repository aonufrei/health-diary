package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.MetricRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MetricService extends AbstractCrudService<Integer, Metric, MetricDto, MetricInDto, MetricRepository> {

	public MetricService(MetricRepository repo, Validator validator) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

	public void addMany(List<MetricInDto> metrics) {
		List<Metric> metricModels = metrics.stream().map(ModelDtoUtil::inDtoToModel).collect(Collectors.toList());
		repo.saveAll(metricModels);
	}

}
