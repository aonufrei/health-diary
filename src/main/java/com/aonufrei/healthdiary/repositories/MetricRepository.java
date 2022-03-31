package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MetricRepository extends JpaRepository<Metric, Integer> {

	@Modifying
	@Query("update metrics m set m.food.id = :foodId where m.id = :metricId")
	void updateFoodForMetric(Integer foodId, Integer metricId);

}
