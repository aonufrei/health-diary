package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AimRepository extends JpaRepository<Aim, Integer> {

	Page<Aim> getAllByPersonId(Pageable pageable, Integer personId);

}
