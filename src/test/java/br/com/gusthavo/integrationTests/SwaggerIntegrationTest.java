package br.com.gusthavo.integrationTests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gusthavo.config.TestConfigs;
import br.com.gusthavo.integrationTests.testContainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest{

	@Test
	void testarPáginaSwagger() {
		var content = given()
		.basePath("/swagger-ui/index.html")
		.port(TestConfigs.SERVER_PORT)
		.when()
			.get()
		.then()
			.statusCode(200)
		.extract()
			.body()
				.asString();
		
		assertTrue(content.contains("swagger-ui"));
	}

}
