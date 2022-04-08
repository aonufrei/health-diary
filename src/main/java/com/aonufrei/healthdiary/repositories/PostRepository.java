package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> getAllByAuthorId(Integer authorId, Pageable pageable);
}
