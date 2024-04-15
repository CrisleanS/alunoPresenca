package com.projeto.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.projeto.models.Workshop;

@RepositoryRestResource(collectionResourceRel = "workshop", path = "workshops")
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

	public Optional<Workshop> findByNameContainingIgnoreCase(String name);

	public Optional<Workshop> findById(Long code);
}
