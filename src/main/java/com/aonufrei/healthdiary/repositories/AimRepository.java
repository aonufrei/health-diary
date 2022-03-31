package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Aim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AimRepository extends JpaRepository<Aim, Integer> {
}
