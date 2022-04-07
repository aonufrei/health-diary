package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.models.BodyReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyReportRepository extends JpaRepository<BodyReport, Integer> {

	@Query("select br from body_reports br where br.person.id = :personId and br.type = :type order by br.createdAt desc")
	Optional<BodyReport> getBodyReportByPersonId(Integer personId, BodyReportType type);

}
