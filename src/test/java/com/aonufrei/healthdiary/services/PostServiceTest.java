package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.models.Post;
import com.aonufrei.healthdiary.repositories.PostRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	private final List<Post> postsInDb = new ArrayList<Post>() {{
		add(Post.builder().content("Content 1").authorId(1).build());
		add(Post.builder().content("Content 2").authorId(1).build());
		add(Post.builder().content("Content 3").authorId(1).build());
		add(Post.builder().content("Content 4").authorId(2).build());
		add(Post.builder().content("Content 5").authorId(3).build());
		add(Post.builder().content("Content 6").authorId(1).build());
	}};

	@Test
	public void getFeedsForPerson() {
		assertNotNull(postRepository);
		assertNotNull(postService);

		when(postRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(postsInDb));

		assertDoesNotThrow(() -> postService.getFeedsForPerson(null, Pageable.ofSize(10)));
		assertEquals(postsInDb.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList()), postService.getFeedsForPerson(null, Pageable.ofSize(10)));
	}

	@Test
	public void getPostsByPerson() {
		assertNotNull(postRepository);
		assertNotNull(postService);

		Integer personId = 1;
		List<Post> expected = postsInDb.stream().filter(it -> Objects.equals(it.getAuthorId(), personId)).collect(Collectors.toList());
		when(postRepository.getAllByAuthorId(personId, Pageable.ofSize(10))).thenReturn(expected);

		assertDoesNotThrow(() -> postService.getPostsByPerson(personId, Pageable.ofSize(10)));
		assertEquals(4, postService.getPostsByPerson(personId, Pageable.ofSize(10)).size());
		assertEquals(expected.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList()), postService.getPostsByPerson(personId, Pageable.ofSize(10)));
	}
}