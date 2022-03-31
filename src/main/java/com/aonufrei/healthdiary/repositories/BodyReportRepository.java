package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.BodyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyReportRepository extends JpaRepository<BodyReport, Integer> {
}
