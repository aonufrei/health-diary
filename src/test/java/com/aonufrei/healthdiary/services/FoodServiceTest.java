package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.FoodInDto;
import com.aonufrei.healthdiary.dtos.FoodWithMetricsInDto;
import com.aonufrei.healthdiary.dtos.MetricInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Food;
import com.aonufrei.healthdiary.models.FoodReport;
import com.aonufrei.healthdiary.models.Metric;
import com.aonufrei.healthdiary.repositories.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.engine.ConstraintViolationImpl.forBeanValidation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

	@Mock
	FoodRepository foodRepository;

	@Mock
	MetricService metricService;

	@Spy
	@InjectMocks
	FoodService foodService;

	private final LocalDate today = LocalDate.now();

	@Test
	public void testAddWithMetrics() {
		assertNotNull(foodRepository);
		assertNotNull(foodService);

		foodService.setValidator(mock(Validator.class, CALLS_REAL_METHODS));
		spy(foodService.getValidator());

		List<MetricInDto> metrics = new ArrayList<>();
		metrics.add(MetricInDto.builder().foodId(1).build());
		FoodWithMetricsInDto input = FoodWithMetricsInDto.builder().food(FoodInDto.builder().build()).metrics(metrics).build();
		doReturn(Food.builder().id(1).build()).when(foodService).add(any());
		doNothing().when(metricService).addMany(anyList());

		assertDoesNotThrow(() -> foodService.addWithMetrics(input));
		verify(foodService.getValidator(), times(3)).validate(any());

		input.setMetrics(null);
		assertThrows(NullPointerException.class, () -> foodService.addWithMetrics(input));
		assertThrows(DataValidationException.class, () -> foodService.addWithMetrics(null));

		when(foodService.getValidator().validate(input)).thenReturn(createDummyViolation(FoodWithMetricsInDto.class));
		assertThrows(DataValidationException.class, () -> foodService.addWithMetrics(input));
	}

	@Test
	public void testAddWithMetricsWithFoodValidation() {
		assertNotNull(foodRepository);
		assertNotNull(foodService);

		foodService.setValidator(mock(Validator.class, CALLS_REAL_METHODS));
		spy(foodService.getValidator());

		List<MetricInDto> metrics = new ArrayList<>();
		metrics.add(MetricInDto.builder().foodId(1).build());
		FoodWithMetricsInDto input = FoodWithMetricsInDto.builder().food(FoodInDto.builder().build()).metrics(metrics).build();
		lenient().doReturn(Food.builder().id(1).build()).when(foodService).add(any());
		lenient().doNothing().when(metricService).addMany(anyList());

		lenient().when(foodService.getValidator().validate(any(FoodInDto.class))).thenReturn(createDummyViolation(FoodInDto.class));
		assertThrows(DataValidationException.class, () -> foodService.addWithMetrics(input));
	}

	@Test
	public void testAddWithMetricsWithMetricValidation() {
		assertNotNull(foodRepository);
		assertNotNull(foodService);

		foodService.setValidator(mock(Validator.class, CALLS_REAL_METHODS));
		spy(foodService.getValidator());

		List<MetricInDto> metrics = new ArrayList<>();
		metrics.add(MetricInDto.builder().foodId(1).build());
		FoodWithMetricsInDto input = FoodWithMetricsInDto.builder().food(FoodInDto.builder().build()).metrics(metrics).build();
		lenient().doReturn(Food.builder().id(1).build()).when(foodService).add(any());
		lenient().doNothing().when(metricService).addMany(anyList());

		lenient().when(foodService.getValidator().validate(any(MetricInDto.class))).thenReturn(createDummyViolation(MetricInDto.class));
		assertThrows(DataValidationException.class, () -> foodService.addWithMetrics(input));
	}

	private <T> Set<ConstraintViolation<T>> createDummyViolation(Class<T> t) {
		System.out.println("Creating violations for class " + t.getName());
		Set<ConstraintViolation<T>> expectedValidationErrors = new HashSet<>();
		ConstraintViolation<T> violationForTest = forBeanValidation(null, null, null, null, null, null, null, null, null, null, null);
		expectedValidationErrors.add(violationForTest);
		return expectedValidationErrors;
	}

}