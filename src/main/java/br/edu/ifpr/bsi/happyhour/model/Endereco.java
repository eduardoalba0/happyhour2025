package br.edu.ifpr.bsi.happyhour.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_endereco")
public class Endereco extends GenericDomain {
	@Column(length = 40, nullable = false)
	private String logradouro;
	@Column(length = 6, nullable = false)
	private String numero;
	@Column(length = 20, nullable = false)
	private String bairro;
	@Column(length = 9, nullable = false)
	private String cep;

	@Transient
	private String enderecoStr;

	@Transient
	public String getEnderecoStr() {
		enderecoStr = this.logradouro + ", " + this.numero;
		return enderecoStr;
	}

	@JoinColumn
	@ManyToOne
	private Cidade cidade;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
