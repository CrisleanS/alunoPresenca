package com.projeto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ProjetoApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void testaConexãoComOBanco() {
		assertNotNull(jdbcTemplate);
		int result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
		assertNotNull(result);
		assertEquals(1, result);
	}
}
