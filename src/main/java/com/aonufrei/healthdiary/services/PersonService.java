package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.PersonDto;
import com.aonufrei.healthdiary.dtos.PersonInDto;
import com.aonufrei.healthdiary.models.Person;
import com.aonufrei.healthdiary.repositories.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
		Person person = repo.getById(id);
		if (person == null) {
			return null;
		}
		return new PersonDto(person);
	}

	public Person getModelById(Integer id) {
		return repo.getById(id);
	}

	@Transactional
	public boolean update(Integer id, PersonInDto inDto) {
		if (id == null || inDto == null) {
			return false;
		}

		Person existing = repo.getById(id);
		if (existing == null) {
			return false;
		}
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

	public boolean exists(Integer id) {
		return repo.existsById(id);
	}

	public Person getPersonToRemain(Integer nPersonId, Person existingPerson) {
		if (nPersonId == null)
			return existingPerson;

		Integer existingPersonId = getPersonId(existingPerson);
		if (!Objects.equals(existingPersonId, nPersonId)) {
			Person nPerson = repo.getById(nPersonId);
			if (nPerson != null) {
				return nPerson;
			}
		}

		return existingPerson;
	}

	public static Integer getPersonId(Person person) {
		return person != null && person.getId() != null ? person.getId() : null;
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
