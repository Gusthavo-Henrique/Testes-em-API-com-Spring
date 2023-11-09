package br.com.gusthavo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.services.PessoaService;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objMapper;

	@MockBean
	private PessoaService service;

	Pessoa p1;

	@BeforeEach
	void setup() {
		p1 = new Pessoa(1L, "Gusthavo1", "05048911202", 20);

	}

	@Test
	void testCreatePessoaController() throws JsonProcessingException, Exception {
		given(service.create(any(Pessoa.class))).willAnswer((invocation) -> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/api/v1/pessoa").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(p1)));

		response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.nome_pes", is(p1.getNome_pes())));
	}

	@Test
	void testFindAllPessoasController() throws JsonProcessingException, Exception {
		List<Pessoa> list = new ArrayList<>();
		list.add(p1);
		list.add(new Pessoa(2L, "Gusthavo2", "42342911202", 19));

		given(service.findAll()).willReturn(list);

		ResultActions response = mockMvc.perform(get("/api/v1/pessoa"));

		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(list.size())));
	}

	@Test
	void testFindByIdPessoaController() throws JsonProcessingException, Exception {
		given(service.findByCod(p1.getCod_pes())).willReturn(p1);

		ResultActions response = mockMvc.perform(get("/api/v1/pessoa/{cod}", p1.getCod_pes()));

		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.nome_pes", is(p1.getNome_pes())));

	}

	@Test
	void testUpdatePessoaController() throws Exception {
		given(service.findByCod(p1.getCod_pes())).willReturn(p1);
		given(service.update(any(Pessoa.class))).willAnswer((invocation) -> invocation.getArgument(0));
		Pessoa p3 = new Pessoa(3L, "Gusthavo3", "05048932123", 18);

		
		ResultActions response = mockMvc.perform(put("/api/v1/pessoa/{cod}", p1.getCod_pes())
				.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(p3)));
		
		response.andDo(print())
			.andExpect(status().isOk());
	}
}
