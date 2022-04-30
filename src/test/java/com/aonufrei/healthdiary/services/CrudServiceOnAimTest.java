package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.AimStatus;
import com.aonufrei.healthdiary.repositories.AimRepository;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.engine.ConstraintViolationImpl.forBeanValidation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudServiceOnAimTest {

	@Mock
	private AimRepository aimRepo;

	@InjectMocks
	private AimService aimService;

	private List<Aim> aimsInDb = new ArrayList<Aim>() {{
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
	public void testGetAllModels() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		Integer limit = 5;
		List<Aim> expectedResults = aimsInDb.stream().limit(limit).collect(Collectors.toList());

		Page<Aim> page = new PageImpl<>(expectedResults);
		lenient().when(aimRepo.findAll(Pageable.ofSize(limit).withPage(0))).thenReturn(page);

		List<Aim> result = aimService.getAllModels(0, limit);

		assertEquals(limit, result.size());
		assertEquals(expectedResults, result);
	}

	@Test
	public void testGetAll() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		int limit = 5;
		List<Aim> expectedModels = aimsInDb.stream().limit(limit).collect(Collectors.toList());
		Page<Aim> expectedPage = new PageImpl<>(expectedModels);
		List<AimDto> expectedResult = expectedModels.stream().map(ModelDtoUtil::modelToDto).collect(Collectors.toList());
		lenient().when(aimRepo.findAll(any(Pageable.class))).thenReturn(expectedPage);

		List<AimDto> result = aimService.getAll(0, limit);

		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult, result);
	}

	@Test
	public void testAdd() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		AimInDto aimInDto = AimInDto.builder().personId(1).status(AimStatus.CREATED).build();
		Aim modelToReturn = ModelDtoUtil.inDtoToModel(aimInDto);
		modelToReturn.setId(1);

		when(aimRepo.save(isA(Aim.class))).thenReturn(modelToReturn);
		aimService.add(aimInDto);
		assertEquals(1, modelToReturn.getId());

	}

	@Test
	public void testGetByModelId() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		final Integer idToGet = 1;
		Aim searchedAim = aimsInDb.stream().filter(it -> Objects.equals(it.getId(), idToGet)).findFirst().orElse(null);
		when(aimRepo.findById(idToGet)).thenReturn(aimsInDb.stream().filter(it -> Objects.equals(it.getId(), idToGet)).findFirst());

		assertNotNull(aimService.getByModelId(idToGet));
		assertEquals(searchedAim, aimService.getByModelId(idToGet));
		assertNull(aimService.getById(100));
	}

	@Test
	public void testGetById() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		final Integer idToGet = 1;
		Aim searchedAim = aimsInDb.stream().filter(it -> Objects.equals(it.getId(), idToGet)).findFirst().orElse(null);
		when(aimRepo.findById(idToGet)).thenReturn(aimsInDb.stream().filter(it -> Objects.equals(it.getId(), idToGet)).findFirst());

		assertNotNull(aimService.getById(idToGet));
		assertEquals(ModelDtoUtil.modelToDto(searchedAim), aimService.getById(idToGet));
		assertNull(aimService.getById(100));
	}

	@Test
	public void testDelete() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		final Integer idToGet = 1;
		doNothing().when(aimRepo).deleteById(idToGet);
		assertDoesNotThrow(() -> aimService.delete(idToGet));
	}

	@Test
	public void testUpdate() {
		assertNotNull(aimRepo);
		assertNotNull(aimService);

		Integer idToGet = 1;
		when(aimRepo.findById(idToGet)).thenReturn(aimsInDb.stream().filter(it -> Objects.equals(it.getId(), idToGet)).findFirst());
		when(aimRepo.save(any(Aim.class))).thenReturn(null);

		assertDoesNotThrow(() -> aimService.update(1, AimInDto.builder().build()));
		assertFalse(aimService.update(null, AimInDto.builder().build()));
		assertFalse(aimService.update(1, null));
		assertTrue(aimService.update(1, AimInDto.builder().build()));
	}

	@Test
	public void testGetAndSetValidator() {
		assertNotNull(aimService);
		assertNull(aimService.getValidator());
		aimService.setValidator(mock(Validator.class));
		assertNotNull(aimService.getValidator());
		aimService.setValidator(null);
		assertNull(aimService.getValidator());
	}

	@Test
	public void testGetRepo() {
		assertNotNull(aimService);
		assertNotNull(aimService.getRepo());
	}

	@Test
	public void testAbstractCrudServiceInit() {
		assertThrows(RuntimeException.class, () -> new AimService(null, null));
	}

	@Test
	public void testValidateOnInsert() {
		assertNotNull(aimService);

		Validator validator = mock(Validator.class);
		aimService.setValidator(validator);
		Set<ConstraintViolation<AimInDto>> expectedValidationErrors = new HashSet<>();
		ConstraintViolation<AimInDto> violation = forBeanValidation(null, null, null, null, null, null, null, null, null, null, null);
		expectedValidationErrors.add(violation);
		when(validator.validate(any(AimInDto.class))).thenReturn(expectedValidationErrors);
		assertThrows(DataValidationException.class, () -> aimService.validateOnInsert(AimInDto.builder().build()));
		aimService.setValidator(null);
	}


}