package com.projeto;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import com.projeto.dtos.AuthetinticationDto;
import com.projeto.dtos.LoginResponseDto;
import com.projeto.dtos.RegisterDto;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Teste usando conexão real com o banco de dados, portante, certifique-se de estar conectado.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	static String mainEmail = UUID.randomUUID().toString().substring(0, 6) + "@email.com";
	static String mainNome = UUID.randomUUID().toString().substring(0, 6);
	static String mainSenha = UUID.randomUUID().toString().substring(0, 10);

	@Test
	@Order(1)
	void deveRetornarUmRegisterBemSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		RegisterDto authetinticationDto = new RegisterDto(mainEmail, mainNome, mainSenha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/register",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				LoginResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(2)
	void cadastroSemEmailDeveRetornarUmRegisterMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		String nome = UUID.randomUUID().toString().substring(0, 6);
		String senha = UUID.randomUUID().toString().substring(0, 10);
		RegisterDto authetinticationDto = new RegisterDto(null, nome, senha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/register",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(3)
	void cadastroComMesmoEmailDeveRetornarUmRegisterMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		String nome = UUID.randomUUID().toString().substring(0, 6);
		String senha = UUID.randomUUID().toString().substring(0, 10);
		RegisterDto authetinticationDto = new RegisterDto(mainEmail, nome, senha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Tenta registrar mesmo usuário
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/register",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(4)
	void deveRetornarUmLoginBemSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		AuthetinticationDto authetinticationDto = new AuthetinticationDto(mainEmail, mainSenha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /login e verifique a resposta
		ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/login",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				LoginResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(5)
	void emailErradoDeveRetornarUmLoginMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		AuthetinticationDto authetinticationDto = new AuthetinticationDto("a" + mainEmail, mainSenha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /login e verifique a resposta
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/login",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(6)
	void senhaErradaDeveRetornarUmLoginMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		AuthetinticationDto authetinticationDto = new AuthetinticationDto(mainEmail, "a" + mainSenha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /login e verifique a resposta
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/login",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private HttpHeaders createJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
