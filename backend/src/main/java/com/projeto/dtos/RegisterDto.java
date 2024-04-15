package com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
		@NotBlank String email,
		@NotBlank String username,
		@NotBlank String password) {

}
