package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodDto;
import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

	private final FoodRepository repo;

	public FoodService(FoodRepository repo) {
		this.repo = repo;
	}

	public List<FoodDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(FoodDto::new).collect(Collectors.toList());
	}

	public FoodDto getById(Integer id) {
		Food food = repo.getById(id);
		if (food == null) {
			return null;
		}
		return new FoodDto(food);
	}

	public Integer add(FoodInDto inDto) {
		if (inDto == null) {
			return null;
		}
		Food food = inDtoToModel(inDto);
		return repo.save(food).getId();
	}

	@Transactional
	public boolean update(Integer foodId, FoodInDto inDto) {
		if (inDto == null || foodId == null) {
			return false;
		}
		Food existing = repo.getById(foodId);
		if (existing == null) {
			return false;
		}
		if (inDto.getName() != null) {
			existing.setName(inDto.getName());
		}

		repo.save(existing);
		return true;
	}

	public boolean delete(Integer id) {
		repo.deleteById(id);
		return true;
	}

	public Food inDtoToModel(FoodInDto inDto) {
		Food food = new Food();
		food.setName(inDto.getName());
		return food;
	}
}
