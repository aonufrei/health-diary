package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.AimDto;
import com.aonufrei.healthdiary.dtos.AimInDto;
import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.AimRepository;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AimService {

	private final AimRepository repo;
	private final PersonRepository personRepo;

	public AimService(AimRepository repo, PersonRepository personRepo) {
		this.repo = repo;
		this.personRepo = personRepo;
	}

	public List<AimDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList().stream()
				.map(AimDto::new).collect(Collectors.toList());
	}

	@Transactional
	public Integer add(AimInDto inDto) {
		Person person = personRepo.getById(inDto.getPersonId());
		Aim nAim = Aim.builder()
				.targetWeight(inDto.getTargetWeight())
				.person(person)
				.status(inDto.getStatus())
				.mentioned(inDto.getMentioned())
				.build();
		return repo.save(nAim).getId();
	}

	public AimDto getById(Integer id) {
		Aim aim = repo.getById(id);
		if (aim == null) {
			return null;
		}
		return new AimDto(aim);
	}

	@Transactional
	public boolean update(Integer id, AimInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}

		Aim existing = repo.getById(id);
		if (!repo.existsById(id)) {
			return false;
		}
		if (inDto.getTargetWeight() != null) {
			existing.setTargetWeight(inDto.getTargetWeight());
		}
		if (inDto.getMentioned() != null) {
			existing.setMentioned(inDto.getMentioned());
		}
		if (inDto.getStatus() != null) {
			existing.setStatus(inDto.getStatus());
		}

		repo.save(existing);
		Integer existingPersonId = getPersonId(existing);
		if (inDto.getPersonId() != null && !Objects.equals(existingPersonId, inDto.getPersonId())
				&& personRepo.existsById(inDto.getPersonId())) {
			repo.updateAimOwner(inDto.getPersonId(), existing.getId());
		}

		return true;
	}

	private Integer getPersonId(Aim aim) {
		if (aim.getPerson() != null && aim.getPerson().getId() != null) {
			return aim.getPerson().getId();
		}
		return null;
	}

	public void deleteAim(Integer id) {
		repo.deleteById(id);
	}
}
