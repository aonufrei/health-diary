package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.FoodReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodReportRepository extends JpaRepository<FoodReport, Integer> {
}
