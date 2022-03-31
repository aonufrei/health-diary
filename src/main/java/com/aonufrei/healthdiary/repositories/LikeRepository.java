package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
}
