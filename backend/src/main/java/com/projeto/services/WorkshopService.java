package com.projeto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projeto.models.Workshop;
import com.projeto.repositories.WorkshopRepository;

@Service
public class WorkshopService {

	@Autowired
	private WorkshopRepository workshopRepository;

	public Workshop create(Workshop workshop) {
		return workshopRepository.save(workshop);
	}

	public Workshop update(Long id, Workshop workshop) {

		Workshop existingWorkshop = workshopRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workshop não existe"));

		existingWorkshop.setName(workshop.getName());
		existingWorkshop.setDate(workshop.getDate());
		return workshopRepository.save(existingWorkshop);
	}
}
