package br.com.gusthavo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.repositories.PessoaRepository;
import br.com.gusthavo.services.exception.PessoaNãoEncontradaException;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

	@Mock
	PessoaRepository repository;

	@InjectMocks
	PessoaService service;

	Pessoa p1;
	Pessoa p2;
	Pessoa p3;

	@BeforeEach
	void setup() {
		p1 = new Pessoa(1L, "Gusthavo1", "05048911202", 20);
		p2 = new Pessoa(2L, "Gusthavo2", "42342911202", 19);
		p3 = new Pessoa(3L, "Gusthavo3", "05048932123", 18);

	}

	@Test
	void testMétodoCreatePessoa() {

		given(repository.save(p1)).willReturn(p1);

		Pessoa savedPessoa = service.create(p1);

		assertThat(savedPessoa).isNotNull();
		assertThat(savedPessoa.getCod_pes()).isEqualTo(1L);
		assertThat(savedPessoa.getCod_pes()).isEqualTo(p1.getCod_pes());
		assertThat(savedPessoa.getNome_pes()).isEqualTo(p1.getNome_pes());
	}

	@Test
	void testMétodoEncontrarTodos() {
		List<Pessoa> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		given(repository.findAll()).willReturn(list);

		List<Pessoa> savedList = service.findAll();

		assertThat(savedList).hasOnlyElementsOfType(Pessoa.class);
		assertThat(savedList.size()).isEqualTo(3);
		assertThat(savedList.get(0).getCod_pes()).isEqualTo(1L);
	}

	@Test
	void testMétodoEncontrarTodosException() {
		given(repository.findAll()).willReturn(Collections.emptyList());

		List<Pessoa> savedList = service.findAll();

		assertThat(savedList).isEmpty();
	}

	@Test
	void testMétodoEncontrarPeloCódigo() {
		given(repository.findById(anyLong())).willReturn(Optional.of(p1));

		Pessoa findedPessoa = service.findByCod(1L);

		assertThat(findedPessoa).isNotNull();
		assertThat(findedPessoa.getCod_pes()).isEqualTo(1L);
	}

	@Test
	void testMétodoUpdatePessoa() {
		given(repository.findById(p1.getCod_pes())).willReturn(Optional.of(p1));
		p1.setNome_pes("Jamile");
		p1.setCpf_pes("00000000000");
		p1.setIdade_pes(18);
		given(repository.save(p1)).willReturn(p1);

		Pessoa updatedPessoa = service.update(p1);

		assertThat(updatedPessoa).isNotNull();
		assertThat(updatedPessoa.getNome_pes()).isEqualTo("Jamile");
	}
}
