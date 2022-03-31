package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.FoodReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FoodReportRepository extends JpaRepository<FoodReport, Integer> {

	@Modifying
	@Query("update food_reports fr set fr.food.id = :foodId, fr.metric.id = :metricId where fr.id = :foodReportId")
	void updateFoodAndMetric(Integer foodId, Integer metricId, Integer foodReportId);

}
