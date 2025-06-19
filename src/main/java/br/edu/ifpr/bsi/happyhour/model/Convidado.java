package br.edu.ifpr.bsi.happyhour.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="tb_convidado")
public class Convidado extends GenericDomain{
	@Column(length=30, nullable=false)
	private String nome;
	@JoinColumn
	@OneToOne(cascade = {CascadeType.ALL})
	private Contato contato;
	
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
