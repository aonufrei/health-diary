package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Aim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AimRepository extends JpaRepository<Aim, Integer> {

	@Modifying
	@Query("update aims a set a.person.id = :ownerId where a.id = :aimId")
	void updateAimOwner(Integer ownerId, Integer aimId);

}
