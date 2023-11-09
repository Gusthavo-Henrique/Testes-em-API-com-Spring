package br.com.gusthavo.integrationTests;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gusthavo.config.TestConfigs;
import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.integrationTests.testContainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PessoaControllerIntegrationTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static Pessoa p1;

	@BeforeAll
	static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		specification = new RequestSpecBuilder().setBasePath("/api/v1/pessoa").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		p1 = new Pessoa(1L, "Gusthavo1", "05048911202", 20);
	}

	@Order(1)
	@Test
	void TestarCriarPessoa() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(p1).when().post()
				.then().statusCode(201).extract().body().asString();

		Pessoa Criadopessoa = objectMapper.readValue(content, Pessoa.class);
		p1 = Criadopessoa;

		assertNotNull(Criadopessoa);
		assertNotNull(Criadopessoa.getCod_pes());
		assertNotNull(Criadopessoa.getNome_pes());
		assertNotNull(Criadopessoa.getCpf_pes());
		assertNotNull(Criadopessoa.getIdade_pes());

		assertEquals("Gusthavo1", p1.getNome_pes());
	}

	@Order(2)
	@Test
	void TestarUpdatePessoa() throws JsonMappingException, JsonProcessingException {
		p1.setNome_pes("Gusthavo2");
		p1.setCpf_pes("00000000000");
		p1.setIdade_pes(19);
		
		var content = given().spec(specification)
				.pathParam("cod", p1.getCod_pes())
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(p1)
				.when()
					.put("{cod}")
				.then()
					.statusCode(200)
						.extract()
							.body()
								.asString();

		Pessoa Criadopessoa = objectMapper.readValue(content, Pessoa.class);
		p1 = Criadopessoa;

		assertNotNull(Criadopessoa);
		assertNotNull(Criadopessoa.getCod_pes());
		assertNotNull(Criadopessoa.getNome_pes());
		assertNotNull(Criadopessoa.getCpf_pes());
		assertNotNull(Criadopessoa.getIdade_pes());
	}
	@Order(3)
	@Test
	void TestarFindById() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification)
		.pathParam("cod", p1.getCod_pes())
		.when()
			.get("{cod}")
		.then()
			.statusCode(200)
				.extract()
					.body()
						.asString();
		Pessoa Criadopessoa = objectMapper.readValue(content, Pessoa.class);

		assertTrue(0 < Criadopessoa.getIdade_pes());
	}
	
	@Order(4)
	@Test
	void TestarFindAll() throws JsonMappingException, JsonProcessingException {	
		Pessoa p2 = new Pessoa(2L, "Gusthavo2", "05048911202", 20);
		given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(p2).when().post()
		.then().statusCode(201);
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		List<Pessoa> list = Arrays.asList(objectMapper.readValue(content, Pessoa[].class));
		
		Pessoa pessoaEncontrada = list.get(0);
		
		assertEquals(list.size(), 2, () -> "O tamanho da lista de pessoa não é " + 2);
		assertTrue(pessoaEncontrada.getCod_pes() > 0 );
	}
	
}
