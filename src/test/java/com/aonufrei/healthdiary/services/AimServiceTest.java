package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.repositories.AimRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AimServiceTest {

	@Mock
	private AimRepository aimRepo;

	@InjectMocks
	private AimService aimService;

	private final List<Aim> aimsInDb = new ArrayList<Aim>() {{
		add(Aim.builder().id(1).status(AimStatus.CREATED).personId(1).build());
		add(Aim.builder().id(2).status(AimStatus.CREATED).personId(2).build());
		add(Aim.builder().id(3).status(AimStatus.CREATED).personId(3).build());
		add(Aim.builder().id(4).status(AimStatus.CREATED).personId(4).build());
		add(Aim.builder().id(5).status(AimStatus.CREATED).personId(5).build());
		add(Aim.builder().id(6).status(AimStatus.CREATED).personId(6).build());
		add(Aim.builder().id(7).status(AimStatus.CREATED).personId(7).build());
		add(Aim.builder().id(8).status(AimStatus.CREATED).personId(8).build());
		add(Aim.builder().id(9).status(AimStatus.CREATED).personId(9).build());
		add(Aim.builder().id(10).status(AimStatus.CREATED).personId(10).build());
		add(Aim.builder().id(11).status(AimStatus.CREATED).personId(11).build());
		add(Aim.builder().id(12).status(AimStatus.CREATED).personId(12).build());
		add(Aim.builder().id(13).status(AimStatus.CREATED).personId(13).build());
	}};

	@Test
	public void getAllByPersonId() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		Integer personId = 1;
		List<Aim> expectedResults = aimsInDb.stream().filter(it -> it.getPersonId().equals(1)).collect(Collectors.toList());
		Page<Aim> expectedPage = new PageImpl<>(expectedResults);

		when(aimRepo.getAllByPersonId(Pageable.ofSize(10).withPage(1), personId)).thenReturn(expectedPage);
		when(aimRepo.getAllByPersonId(Pageable.ofSize(10).withPage(1), 100)).thenReturn(new PageImpl<>(new ArrayList<>()));

		assertDoesNotThrow(() -> aimService.getAllByPersonId(personId, 10, 1));
		assertEquals(expectedResults.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList()), aimService.getAllByPersonId(personId, 10, 1));
		assertEquals(Collections.EMPTY_LIST, aimService.getAllByPersonId(100, 10, 1));
	}
}