package br.com.gusthavo.entidade;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod_pes;

	@Column(name = "nomePes")
	private String nome_pes;

	@Column(name = "cpfPes")
	private String cpf_pes;

	@Column(name = "idadePes")
	private int idade_pes;

	public Pessoa() {

	}

	public Pessoa(Long cod_pes, String nome_pes, String cpf_pes, int idade_pes) {
		super();
		this.cod_pes = cod_pes;
		this.nome_pes = nome_pes;
		this.cpf_pes = cpf_pes;
		this.idade_pes = idade_pes;
	}

	public Long getCod_pes() {
		return cod_pes;
	}

	public void setCod_pes(Long cod_pes) {
		this.cod_pes = cod_pes;
	}

	public String getNome_pes() {
		return nome_pes;
	}

	public void setNome_pes(String nome_pes) {
		this.nome_pes = nome_pes;
	}

	public String getCpf_pes() {
		return cpf_pes;
	}

	public void setCpf_pes(String cpf_pes) {
		this.cpf_pes = cpf_pes;
	}

	public int getIdade_pes() {
		return idade_pes;
	}

	public void setIdade_pes(int idade_pes) {
		this.idade_pes = idade_pes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cod_pes, cpf_pes, idade_pes, nome_pes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(cod_pes, other.cod_pes) && Objects.equals(cpf_pes, other.cpf_pes)
				&& idade_pes == other.idade_pes && Objects.equals(nome_pes, other.nome_pes);
	}

}
