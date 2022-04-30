package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.repositories.MetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

	@Mock
	private MetricRepository metricRepository;

	@InjectMocks
	private MetricService metricService;

	@Test
	void testAddMany() {
		when(metricRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
		assertDoesNotThrow(() -> metricService.addMany(new ArrayList<>()));
	}
}