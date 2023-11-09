package br.com.gusthavo.repositoriesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.integrationTests.testContainers.AbstractIntegrationTest;
import br.com.gusthavo.repositories.PessoaRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PessoaRepositoryTest extends AbstractIntegrationTest{

	@Autowired
	private PessoaRepository repository;

	@Test
	@DisplayName("Testar se o repository salvar pessoa está salvando corretamente")
	void testDadoUmObjetoPessoa_DeveRetornar_SalvarPessoa() {

		Pessoa pessoa1 = new Pessoa(1L, "Gusthavo", "05048911202", 20);
		Pessoa pessoa2 = new Pessoa(2L, "Jamile", "00000000000", 19);

		repository.save(pessoa1);

		assertNotNull(pessoa1);
		assertEquals(20, pessoa1.getIdade_pes());
		assertEquals(2L, pessoa2.getCod_pes());
	}

	@Test
	@DisplayName("Testar se o repositorio apos salvar, retorna uma lista corretamente")
	void testRetornasLista_PessoasSalvas() {
		Pessoa pessoa1 = new Pessoa(1L, "Gusthavo", "05048911202", 20);
		Pessoa pessoa2 = new Pessoa(2L, "Jamile", "00000000000", 19);

		repository.save(pessoa1);
		repository.save(pessoa2);

		List<Pessoa> list = repository.findAll();

		assertNotNull(list.size());
		assertEquals(2, list.size());
		assertEquals(2L, list.get(1).getCod_pes());

	}

	@Test
	@DisplayName("Testar se o repository retorna uma pessoa passado o id")
	void testRetornarCorretamento_FindByCod() {

		Pessoa pessoa1 = new Pessoa(1L, "Gusthavo", "05048911202", 20);
		Pessoa pessoa2 = new Pessoa(2L, "Jamile", "00000000000", 19);

		repository.save(pessoa1);
		repository.save(pessoa2);

		Pessoa find1 = repository.findById(pessoa1.getCod_pes()).get();
		Pessoa find2 = repository.findById(pessoa2.getCod_pes()).get();

		assertNotNull(find2);
		assertEquals(pessoa2.getCod_pes(), find2.getCod_pes());
	}

	@Test
	void testeUpdatePessoaRepository() {
		Pessoa pessoa1 = new Pessoa(1L, "Gusthavo", "05048911202", 20);
		repository.save(pessoa1);

		Pessoa updatePessoa = repository.findById(pessoa1.getCod_pes()).get();
		updatePessoa.setCpf_pes("11111111111");
		updatePessoa.setNome_pes("Jamile");
		updatePessoa.setIdade_pes(19);
		repository.save(updatePessoa);

		assertNotNull(updatePessoa.getCod_pes());
		assertEquals("Jamile", updatePessoa.getNome_pes());
		assertNotEquals(pessoa1.getNome_pes(), updatePessoa.getNome_pes());

	}

	@Test
	@DisplayName("Testar se o repository deleta")
	void testDeletePessoa() {

		Pessoa pessoa1 = new Pessoa(1L, "Gusthavo", "05048911202", 20);
		Pessoa pessoa2 = new Pessoa(2L, "Jamile", "00000000000", 19);

		repository.save(pessoa1);
		repository.save(pessoa2);

		//assertNotNull(pessoa2);
		//assertNotNull(pessoa1);
		
		repository.deleteById(pessoa1.getCod_pes());
		Optional<Pessoa> teste = repository.findById(pessoa1.getCod_pes());
		assertTrue(teste.isEmpty());
	}
}
