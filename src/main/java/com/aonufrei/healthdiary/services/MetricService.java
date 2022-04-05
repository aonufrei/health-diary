package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.MetricRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class MetricService extends AbstractCrudService<Integer, Metric, MetricDto, MetricInDto, MetricRepository> {

	public MetricService(MetricRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
	}

}
