package com.projeto;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import com.projeto.dtos.LoginResponseDto;
import com.projeto.dtos.RegisterDto;
import com.projeto.models.Workshop;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import java.util.Date;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Teste usando conexão real com o banco de dados, portante, certifique-se de estar conectado.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkshopControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	static String mainEmail = UUID.randomUUID().toString().substring(0, 6) + "@email.com";
	static String mainNome = UUID.randomUUID().toString().substring(0, 6);
	static String mainSenha = UUID.randomUUID().toString().substring(0, 10);
	static String token = "";
	static Random random = new Random();
	static Long id;

	@Test
	@Order(1)
	public void deveRetornarUmRegisterBemSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		RegisterDto authetinticationDto = new RegisterDto(mainEmail, mainNome, mainSenha);
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<LoginResponseDto> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/auth/register",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				LoginResponseDto.class);

		token = response.getBody().token();
	}

	@Test
	@Order(2)
	void deveRetornarUmCadastroBemSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		Workshop authetinticationDto = new Workshop("ANTES", new Date());
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<Workshop> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/workshop/create",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeadersWithToken(token)),
				Workshop.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		id = response.getBody().getId();

	}

	@Test
	@Order(3)
	void deveAlterarUmCadastroExistente() throws Exception {

		// Crie um objeto e converte para JSON
		Workshop authetinticationDto = new Workshop("DEPOIS", new Date());
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<Workshop> response = restTemplate.exchange(
				"http://localhost:" + port + "/workshop/update/" + id, HttpMethod.PUT,
				new HttpEntity<>(authetinticationDtoJson, createJsonHeadersWithToken(token)),
				Workshop.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(4)
	void testeSemTokenDeveRetornarUmCadastroMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		Workshop authetinticationDto = new Workshop("TESTE SEM TOKEN", new Date());
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/workshop/create",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeaders()),
				String.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	@Test
	@Order(4)
	void valorNuloDeveRetornarUmCadastroMalSucedido() throws Exception {

		// Crie um objeto e converte para JSON
		Workshop authetinticationDto = new Workshop(null, new Date());
		String authetinticationDtoJson = new ObjectMapper().writeValueAsString(authetinticationDto);

		// Envie a solicitação POST para o endpoint /register e verifique a resposta
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/workshop/create",
				new HttpEntity<>(authetinticationDtoJson, createJsonHeadersWithToken(token)),
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	static private HttpHeaders createJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	static private HttpHeaders createJsonHeadersWithToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		return headers;
	}

}
