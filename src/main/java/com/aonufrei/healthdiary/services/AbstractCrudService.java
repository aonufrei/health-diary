package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.exceptions.DataValidationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractCrudService<ID, M, MD, MID, R extends JpaRepository<M, ID>> {

	protected final R repo;
	protected Validator validator;

	protected final Function<MID, M> inDtoToModelFunction;
	protected final Function<M, MD> modelToDtoFunction;
	protected final BiConsumer<M, MID> updateFunction;

	protected AbstractCrudService(R repo, Function<MID, M> inDtoToModelFunction, Function<M, MD> modelToDtoFunction, BiConsumer<M, MID> updateFunction) {
		if (repo == null || inDtoToModelFunction == null || modelToDtoFunction == null || updateFunction == null) {
			throw new RuntimeException("Crud service was initialized incorrectly");
		}

		this.repo = repo;
		this.inDtoToModelFunction = inDtoToModelFunction;
		this.modelToDtoFunction = modelToDtoFunction;
		this.updateFunction = updateFunction;
	}

	public List<M> getAllModels(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList();
	}

	public List<MD> getAll(int page, int pageSize) {
		return getAllModels(page, pageSize).stream().filter(Objects::nonNull).map(modelToDtoFunction).collect(Collectors.toList());
	}

	public M add(MID inDto) {
		if (!validateOnInsert(inDto)) return null;
		M model = inDtoToModelFunction.apply(inDto);
		return repo.save(model);
	}

	public MD getById(ID id) {
		M model = getByModelId(id);
		return modelToDtoFunction.apply(model);
	}

	public M getByModelId(ID id) {
		return repo.findById(id).orElse(null);
	}

	public void delete(ID id) {
		repo.deleteById(id);
	}

	public boolean update(ID id, MID inDto) {
		if (id == null || !validateOnUpdate(inDto)) {
			return false;
		}
		M existing = repo.findById(id).orElse(null);
		if (existing == null) return false;

		updateFunction.accept(existing, inDto);
		repo.save(existing);

		return true;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public Validator getValidator() {
		return validator;
	}

	public R getRepo() {
		return repo;
	}

	public boolean validateOnInsert(MID inDto) {
		if (inDto == null) return false;

		if (validator != null) {
			Set<ConstraintViolation<MID>> validationErrors = validator.validate(inDto);
			validationErrors.stream().findFirst().ifPresent(error -> {
				throw new DataValidationException(error.getMessage());
			});
		}

		return true;
	}

	public boolean validateOnUpdate(MID inDto) {
		if (inDto == null) return false;
		return true;
	}

}
