package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.BodyReportDto;
import com.aonufrei.healthdiary.dtos.BodyReportInDto;
import com.aonufrei.healthdiary.models.BodyReport;
import com.aonufrei.healthdiary.repositories.BodyReportRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BodyReportService {

	private final BodyReportRepository repo;
	private final PersonService personService;

	public BodyReportService(BodyReportRepository repo, PersonService personService) {
		this.repo = repo;
		this.personService = personService;
	}

	public List<BodyReportDto> getAll(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList()
				.stream().map(BodyReportDto::new).collect(Collectors.toList());
	}

	public BodyReportDto getById(Integer id) {
		BodyReport bodyReport = repo.getById(id);
		if (bodyReport == null) {
			return null;
		}
		return new BodyReportDto(bodyReport);
	}

	public Integer add(BodyReportInDto inDto) {
		BodyReport bodyReport = inDtoToModel(inDto);
		return repo.save(bodyReport).getId();
	}

	@Transactional
	public boolean update(Integer id, BodyReportInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}
		BodyReport existing = repo.getById(id);
		if (existing == null) {
			return false;
		}

		if (inDto.getHeight() != null) {
			existing.setHeight(inDto.getHeight());
		}
		if (inDto.getWeight() != null) {
			existing.setWeight(inDto.getWeight());
		}
		if (inDto.getLoggedTime() != null) {
			existing.setLoggedTime(inDto.getLoggedTime());
		}

		repo.save(existing);
		if (inDto.getPersonId() != null && existing.getPerson() != null && !Objects.equals(existing.getPerson().getId(), inDto.getPersonId())
				&& personService.exists(inDto.getPersonId())) {
			repo.updateBodyReportOwner(inDto.getPersonId(), existing.getId());
		}

		return true;
	}

	public boolean delete(Integer id) {
		repo.deleteById(id);
		return true;
	}

	public BodyReport inDtoToModel(BodyReportInDto inDto) {
		if (inDto == null) {
			return null;
		}

		BodyReport bodyReport = new BodyReport();
		bodyReport.setHeight(inDto.getHeight());
		bodyReport.setWeight(inDto.getWeight());
		if (inDto.getPersonId() != null && personService.exists(inDto.getPersonId())) {
			bodyReport.setPerson(personService.getModelById(inDto.getPersonId()));
		}
		bodyReport.setLoggedTime(inDto.getLoggedTime());
		return bodyReport;
	}
}
