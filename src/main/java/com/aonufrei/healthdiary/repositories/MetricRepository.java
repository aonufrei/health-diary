package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Integer> {
}
