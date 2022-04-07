package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.dtos.FoodWithMetricsInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;


@Service
public class FoodService extends AbstractCrudService<Integer, Food, FoodDto, FoodInDto, FoodRepository> {

	private final MetricService metricService;

	public FoodService(FoodRepository repo, Validator validator, MetricService metricService) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		this.metricService = metricService;
		setValidator(validator);
	}

	@Transactional
	public boolean addWithMetrics(FoodWithMetricsInDto inDto) {

		validator.validate(inDto).stream().findFirst().ifPresent(error -> {
			throw new DataValidationException(error.getMessage());
		});

		validator.validate(inDto.getFood()).stream().findFirst().ifPresent(error -> {
			throw new DataValidationException(error.getMessage());
		});

		inDto.getMetrics().forEach(it ->
				validator.validate(it).stream().findFirst().ifPresent(error -> {
					throw new DataValidationException(error.getMessage());
				})
		);

		Integer foodId = add(inDto.getFood()).getId();

		inDto.getMetrics().forEach(it -> it.setFoodId(foodId));
		metricService.addMany(inDto.getMetrics());

		return true;
	}

}
