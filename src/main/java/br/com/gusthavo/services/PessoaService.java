package br.com.gusthavo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gusthavo.entidade.Pessoa;
import br.com.gusthavo.repositories.PessoaRepository;
import br.com.gusthavo.services.exception.PessoaNãoEncontradaException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	public Pessoa findByCod(Long Cod) {
		return repository.findById(Cod).orElseThrow(() -> new PessoaNãoEncontradaException("Pessoa com o código "+Cod+" não foi encontrada ou não existe"));
	}

	public Pessoa create(Pessoa pessoa) {
		Pessoa obj = new Pessoa();
		obj.setCod_pes(pessoa.getCod_pes());
		obj.setNome_pes(pessoa.getNome_pes());
		obj.setCpf_pes(pessoa.getCpf_pes());
		obj.setIdade_pes(pessoa.getIdade_pes());
		
		return repository.save(obj);
	}

	public Pessoa update(Pessoa pessoa) {
		repository.findById(pessoa.getCod_pes());
		Pessoa obj = new Pessoa();
		obj.setCod_pes(pessoa.getCod_pes());
		obj.setNome_pes(pessoa.getNome_pes());
		obj.setCpf_pes(pessoa.getCpf_pes());
		obj.setIdade_pes(pessoa.getIdade_pes());
		
		return repository.save(obj);
	}

	public void delete(Long cod) {
		repository.deleteById(cod);
	}

}
