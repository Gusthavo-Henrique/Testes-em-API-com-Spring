package br.com.gusthavo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService service;

	@GetMapping
	@Operation(summary = "Listando todas as pessoas.", description = "Retornar uma listas das pessoas cadastradas.", tags = "{Pessoa}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucess", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pessoa.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "401", description = "Bad Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), })
	public List<Pessoa> findAll() {
		return service.findAll();
	}

	@GetMapping(value = "/{cod}")
	@Operation(summary = "Listar uma pessoa pelo código único.", description = "Retornar uma pessoa passando como parâmetro seu código único", tags = "{Pessoa}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucess", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), })
	public Pessoa findByCod(@PathVariable(name = "cod") Long cod) {
		return service.findByCod(cod);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Criar/Cadastrar uma pessoa", description = "Cadastra uma pessoa passando um JSON como parâmetro no body", tags = "{Pessoa}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucess created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), })
	public Pessoa createPessoa(@RequestBody Pessoa pessoa) {
		return service.create(pessoa);
	}

	@PutMapping(value = "/{cod}")
	@Operation(summary = "Atualiza o cadastro de uma pessoa", description = "Atualiza uma pessoa passando o código e o JSON como parâmetro no body", tags = "{Pessoa}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucess", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), })
	public Pessoa updatePessoa(@RequestBody Pessoa pessoa, @PathVariable(name = "cod") Long cod) {
		return service.update(pessoa);
	}

	@DeleteMapping(value = "/{cod}")
	@Operation(summary = "Delete do banco de dados o cadastro de uma pessoa", description = "Delete um cadastro passando o código único dessa pessoa", tags = "{Pessoa}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pessoa.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),

	})
	public ResponseEntity<?> deletePessoa(@PathVariable(name = "cod") Long cod) {
		service.delete(cod);
		return ResponseEntity.noContent().build();
	}

}
