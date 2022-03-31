package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
