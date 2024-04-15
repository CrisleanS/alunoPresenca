package com.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.dtos.AuthetinticationDto;
import com.projeto.dtos.LoginResponseDto;
import com.projeto.dtos.RegisterDto;
import com.projeto.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	AuthService authService;

	/**
	 * {
	 *    "email": "teste@teste",
	 *    "password": "teste123"
	 *  }
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody AuthetinticationDto authetinticationDto) {
		LoginResponseDto loginResponse = authService.login(authetinticationDto);
		return ResponseEntity.ok(loginResponse);
	}

	/**
	 * {
	 *    "email": "teste@teste",
	 *    "username": "teste",
	 *    "password": "teste123"
	 *  }
	 */
	@PostMapping("/register")
	public ResponseEntity<LoginResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {
		LoginResponseDto loginResponse = authService.register(registerDto);
		return ResponseEntity.ok(loginResponse);
	}
}
