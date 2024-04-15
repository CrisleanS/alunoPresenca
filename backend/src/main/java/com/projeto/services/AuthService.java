package com.projeto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import com.projeto.config.TokenService;
import com.projeto.dtos.AuthetinticationDto;
import com.projeto.dtos.LoginResponseDto;
import com.projeto.dtos.RegisterDto;
import com.projeto.dtos.UserDto;
import com.projeto.models.User;
import com.projeto.repositories.UserRepository;
import jakarta.validation.Valid;

@Service
public class AuthService implements UserDetailsService {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email);
	}

	public LoginResponseDto login(@Valid @RequestBody AuthetinticationDto data) {

		//resolve problema da dependencia circular 
		authenticationManager = context.getBean(AuthenticationManager.class);

		var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = authenticationManager.authenticate(emailPassword);
		var user = (User) auth.getPrincipal();
		var token = tokenService.generateToken(user);
		return new LoginResponseDto(token, new UserDto(user.getId(), user.getEmail(), user.getUsername()));
	}

	public LoginResponseDto register(@RequestBody RegisterDto registerDto) {

		if (userRepository.findByEmail(registerDto.email()) == null) {

			String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
			User newUser = new User(registerDto.email(), registerDto.username(), encryptedPassword);
			userRepository.save(newUser);

			AuthetinticationDto authDto = new AuthetinticationDto(registerDto.email(), registerDto.password());
			return login(authDto);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario Existente");
	}

}
