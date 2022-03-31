package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

	@Modifying
	@Query("update posts p set p.author.id = :authorId where p.id = :postId")
	void updatePostAuthor(Integer authorId, Integer postId);
}
