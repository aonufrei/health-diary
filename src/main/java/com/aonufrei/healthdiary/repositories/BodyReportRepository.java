package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.BodyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyReportRepository extends JpaRepository<BodyReport, Integer> {

	@Modifying
	@Query("update body_reports bp set bp.person.id = :ownerId where bp.id = :reportId")
	void updateBodyReportOwner(Integer ownerId, Integer reportId);

}
