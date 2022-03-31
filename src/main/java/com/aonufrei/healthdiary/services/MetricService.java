package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.MetricDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import com.aonufrei.healthdiary.repositories.MetricRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricService {

	private final MetricRepository repo;
	private final FoodRepository foodRepository;

	public MetricService(MetricRepository repo, FoodRepository foodRepository) {
		this.repo = repo;
		this.foodRepository = foodRepository;
	}

	public List<MetricDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(MetricDto::new).collect(Collectors.toList());
	}

	public MetricDto getById(Integer id) {
		Metric metric = repo.getById(id);
		if (metric == null) {
			return null;
		}
		return new MetricDto(metric);
	}

	public Integer add(MetricInDto inDto) {
		Metric metric = inDtoToMetric(inDto);
		if (metric == null) {
			return null;
		}
		return repo.save(metric).getId();
	}

	@Transactional
	public boolean update(Integer metricId, MetricInDto inDto) {
		if (metricId == null || inDto == null) {
			return false;
		}
		Metric existing = repo.getById(metricId);

		if (existing == null) {
			return false;
		}

		if (inDto.getName() != null) {
			existing.setName(inDto.getName());
		}
		if (inDto.getValue() != null) {
			existing.setValue(inDto.getValue());
		}
		repo.save(existing);
		if (inDto.getFoodId() != null && foodRepository.existsById(inDto.getId())) {
			repo.updateFoodForMetric(inDto.getFoodId(), existing.getId());
		}

		return true;

	}

	public boolean delete(Integer id) {
		repo.deleteById(id);
		return true;
	}

	public boolean deleteMany(List<Integer> ids) {
		repo.deleteAllById(ids);
		return true;
	}

	public Metric inDtoToMetric(MetricInDto inDto) {
		if (inDto == null) {
			return null;
		}
		Metric metric = new Metric();
		metric.setName(inDto.getName());
		metric.setValue(inDto.getValue());
		metric.setFood(inDto.getFoodId() != null ? foodRepository.getById(inDto.getFoodId()) : null);
		return metric;
	}
}
