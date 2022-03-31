package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
