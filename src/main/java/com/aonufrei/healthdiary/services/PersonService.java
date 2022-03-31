package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

	private final PersonRepository repo;

	public PersonService(PersonRepository repo) {
		this.repo = repo;
	}

	public List<PersonDto> getAll(int page, int size) {
		return repo.findAll(Pageable.ofSize(size).withPage(page)).toList().stream()
				.map(PersonDto::new).collect(Collectors.toList());
	}

	public Integer add(PersonInDto dto) {
		Person person = Person.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.imagePath(dto.getImagePath())
				.build();

		return repo.save(person).getId();
	}

	public PersonDto getById(Integer id) {
		return new PersonDto(repo.getById(id));
	}

	@Transactional
	public boolean update(Integer id, PersonInDto inDto) {
		Person existing = repo.getById(id);
		if (inDto.getName() != null) {
			existing.setName(inDto.getName());
		}
		if (inDto.getEmail() != null) {
			existing.setEmail(inDto.getEmail());
		}
		if (inDto.getImagePath() != null) {
			existing.setImagePath(inDto.getImagePath());
		}
		return repo.save(existing).getId() > 0;
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
