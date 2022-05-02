package com.aonufrei.healthdiary.repositories;

import com.aonufrei.healthdiary.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LikeRepositoryTest {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void testDeleteLikeByPersonAndPost() {
		assertNotNull(entityManager);
		assertNotNull(likeRepository);

		entityManager.merge(Person.builder().id(1).name("Test 1").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());
		entityManager.merge(Person.builder().id(2).name("Test 2").dob(LocalDate.now()).gender(Gender.MALE).activity(PhysicalActivity.ACTIVE).email("email").build());

		entityManager.merge(Post.builder().id(1).authorId(1).content("Test Content 1").build());
		entityManager.merge(Post.builder().id(2).authorId(1).content("Test Content 2").build());
		entityManager.merge(Post.builder().id(3).authorId(2).content("Test Content 3").build());
		entityManager.merge(Post.builder().id(4).authorId(2).content("Test Content 4").build());

		entityManager.merge(Like.builder().id(1).postId(1).authorId(2).build());
		entityManager.merge(Like.builder().id(2).postId(2).authorId(2).build());
		entityManager.merge(Like.builder().id(3).postId(3).authorId(1).build());
		entityManager.merge(Like.builder().id(4).postId(4).authorId(1).build());

		entityManager.flush();

		likeRepository.deleteLikeByPersonAndPost(2, 1);
		assertEquals(3, likeRepository.findAll().size());
		assertFalse(likeRepository.existsById(1));
		likeRepository.deleteLikeByPersonAndPost(2, 2);
		assertEquals(2, likeRepository.findAll().size());
		assertFalse(likeRepository.existsById(2));
		likeRepository.deleteLikeByPersonAndPost(1, 3);
		assertEquals(1, likeRepository.findAll().size());
		assertFalse(likeRepository.existsById(3));
		likeRepository.deleteLikeByPersonAndPost(1, 4);
		assertEquals(0, likeRepository.findAll().size());
		assertFalse(likeRepository.existsById(4));

		assertEquals(2, personRepository.findAll().size());
		assertEquals(4, postRepository.findAll().size());
	}
}