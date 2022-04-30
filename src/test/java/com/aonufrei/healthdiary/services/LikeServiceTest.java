package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.repositories.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

	@Mock
	private LikeRepository likeRepository;

	@InjectMocks
	public LikeService likeService;

	@Test
	void testDeleteLikeByPersonAndPost() {
		doNothing().when(likeRepository).deleteLikeByPersonAndPost(anyInt(), anyInt());
		assertDoesNotThrow(() -> likeService.deleteLikeByPersonAndPost(1, 1));
	}
}