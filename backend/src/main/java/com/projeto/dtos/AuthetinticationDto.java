package com.projeto.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthetinticationDto(
		@NotBlank String email,
		@NotBlank String password) {

}