package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.FoodReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FoodReportRepository extends JpaRepository<FoodReport, Integer> {


	@Query("select fr from food_reports fr where fr.person.id = :personId and fr.type = :type and fr.reportedDate = :date")
	List<FoodReport> getFoodReportByPersonAndMeal(Integer personId, FoodReportType type, LocalDate date);

}
