package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Integer> {

	@Modifying
	@Query(value = "delete from likes where post_id=:postId and person_id = :personId", nativeQuery = true)
	void deleteLikeByPersonAndPost(Integer personId, Integer postId);
}
