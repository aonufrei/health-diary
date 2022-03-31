package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Integer> {

	@Modifying
	@Query(value = "insert into likes(person_id, post_id) values(:authorId, :postId)", nativeQuery = true)
	Integer likePost(Integer authorId, Integer postId);

	@Modifying
	@Query("update likes set author.id = :authorId where id=:likeId")
	void updateLikeAuthor(Integer authorId, Integer likeId);

	@Modifying
	@Query("update likes set post.id = :postId where id=:likeId")
	void updateLikePost(Integer postId, Integer likeId);

	@Modifying
	@Query("update likes set author.id = :authorId, post.id = :postId where id=:likeId")
	void updateLikeAuthorAndPost(Integer authorId, Integer postId, Integer likeId);
}
