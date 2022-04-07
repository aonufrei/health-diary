package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.springframework.stereotype.Service;


@Service
public class FoodService extends AbstractCrudService<Integer, Food, FoodDto, FoodInDto, FoodRepository> {

	public FoodService(FoodRepository repo) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
	}

}
