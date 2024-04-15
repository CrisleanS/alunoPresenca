package com.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.models.Workshop;
import com.projeto.services.WorkshopService;

@RestController
@RequestMapping("workshop")
public class WorkshopController {

	@Autowired
	WorkshopService workshopService;

	/**
	 *  {
	 *    "name": "Workshop de Spring Boot",
	 *    "date": "2024-04-14"
	 *  }
	 */
	@PostMapping("/create")
	public ResponseEntity<Workshop> register(@RequestBody Workshop workshop) {
		Workshop createdWorkshop = workshopService.create(workshop);
		return ResponseEntity.ok(createdWorkshop);
	}

	/**
	 *  {
	 *    "name": "Workshop de Spring Boot",
	 *    "date": "2024-04-14"
	 *  }
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Workshop> update(@PathVariable Long id, @RequestBody Workshop workshop) {
		Workshop updatedWorkshop = workshopService.update(id, workshop);
		return ResponseEntity.ok(updatedWorkshop);
	}
}