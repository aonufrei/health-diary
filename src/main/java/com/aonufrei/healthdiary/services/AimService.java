package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.models.Aim;
import com.aonufrei.healthdiary.repositories.AimRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AimService {

	private final AimRepository repo;

	public AimService(AimRepository repo) {
		this.repo = repo;
	}

	public List<Aim> getAllAims(int page, int pageSize) {
		return repo.findAll(Pageable.ofSize(pageSize).withPage(page)).toList();
	}

	public Aim addAim(Aim aim) {
		return repo.save(aim);
	}

	public Aim getAimById(Integer id) {
		return repo.getById(id);
	}

	public boolean updateAim(Integer id, Aim aim) {
		if (!repo.existsById(id)) {
			return false;
		}
		aim.setId(id);
		return repo.save(aim).getId() > 0;
	}

	public void deleteAim(Integer id) {
		repo.deleteById(id);
	}
}
