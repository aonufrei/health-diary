package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodReportDto;
import com.aonufrei.healthdiary.dtos.FoodReportInDto;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.repositories.FoodReportRepository;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import com.aonufrei.healthdiary.repositories.MetricRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodReportService {

	private final FoodReportRepository repo;
	private final FoodRepository foodRepository;
	private final MetricRepository metricRepository;

	public FoodReportService(FoodReportRepository repo, FoodRepository foodRepository, MetricRepository metricRepository) {
		this.repo = repo;
		this.foodRepository = foodRepository;
		this.metricRepository = metricRepository;
	}

	public List<FoodReportDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(FoodReportDto::new).collect(Collectors.toList());
	}

	public FoodReportDto getById(Integer id) {
		if (id == null) {
			return null;
		}
		FoodReport foodReport = repo.getById(id);
		if (foodReport == null) {
			return null;
		}
		return new FoodReportDto(foodReport);
	}

	public Integer add(FoodReportInDto inDto) {
		FoodReport foodReport = inDtoToModel(inDto);
		if (foodReport == null) {
			return null;
		}
		return repo.save(foodReport).getId();
	}

	@Transactional
	public boolean update(Integer id, FoodReportInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}

		FoodReport existing = repo.getById(id);

		if (inDto.getAmount() != null) {
			existing.setAmount(inDto.getAmount());
		}
		if (inDto.getReportedDate() != null) {
			existing.setReportedDate(inDto.getReportedDate());
		}

		repo.save(existing);

		Integer metricToSet;
		Integer existingMetricId = getMetricId(existing);
		if (inDto.getMetricId() != null && !Objects.equals(existingMetricId, inDto.getMetricId()) && metricRepository.existsById(inDto.getMetricId())) {
			metricToSet = inDto.getMetricId();
		} else {
			metricToSet = existingMetricId;
		}

		Integer foodToSet;
		Integer existingFoodId = getFoodId(existing);
		if (inDto.getMetricId() != null && !Objects.equals(existingFoodId, inDto.getFoodId()) && metricRepository.existsById(inDto.getFoodId())) {
			foodToSet = inDto.getFoodId();
		} else {
			foodToSet = existingFoodId;
		}

		repo.updateFoodAndMetric(foodToSet, metricToSet, existing.getId());
		return true;
	}

	public Integer getMetricId(FoodReport foodReport) {
		return foodReport.getMetric() != null ? foodReport.getMetric().getId() : null;
	}

	public Integer getFoodId(FoodReport foodReport) {
		return foodReport.getFood() != null ? foodReport.getFood().getId() : null;
	}

	public boolean delete(Integer id) {
		repo.deleteById(id);
		return true;
	}


	public FoodReport inDtoToModel(FoodReportInDto inDto) {
		if (inDto == null) {
			return null;
		}
		FoodReport foodReport = new FoodReport();
		foodReport.setFood(inDto.getFoodId() != null ? foodRepository.getById(inDto.getFoodId()) : null);
		foodReport.setAmount(inDto.getAmount());
		foodReport.setMetric(inDto.getMetricId() != null ? metricRepository.getById(inDto.getMetricId()) : null);
		foodReport.setReportedDate(inDto.getReportedDate());
		return foodReport;
	}

}
