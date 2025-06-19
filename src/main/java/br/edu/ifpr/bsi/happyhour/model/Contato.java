package br.edu.ifpr.bsi.happyhour.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_contato")
public class Contato extends GenericDomain {
	@Column(length = 13, nullable = true)
	private String telefone;
	@Column(length = 14, nullable = true)
	private String celular;
	@Column(length = 40, nullable = true)
	private String email;

	@Transient
	public String contatoStr;

	@Transient
	public String getContatoStr() {
		contatoStr = "";
		if (!this.telefone.trim().equals(""))
			contatoStr = this.telefone;
		else if (!this.celular.trim().equals(""))
			contatoStr = this.celular;
		else
			contatoStr = this.email;
		return contatoStr;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
