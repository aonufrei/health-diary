package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.FoodReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FoodReportRepository extends JpaRepository<FoodReport, Integer> {

	@Query("select fr from food_reports fr where fr.person.id = :personId and fr.reportedDate = :date")
	List<FoodReport> getFoodReportByPersonAndDay(Integer personId, LocalDate date);

	@Query("select fr from food_reports fr where fr.person.id = :personId and fr.reportedDate between :fromDate and :toDate")
	List<FoodReport> getFoodReportByPersonAndDateRange(Integer personId, LocalDate fromDate, LocalDate toDate);
}
