package com.projeto.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.userdetails.UserDetails;

import com.projeto.models.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

	UserDetails findByEmail(String email);

	public Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

	public Optional<User> findByEmailIgnoreCase(String email);

	public Optional<User> findById(Long code);
}