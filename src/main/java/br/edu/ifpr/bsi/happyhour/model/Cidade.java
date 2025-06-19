package br.edu.ifpr.bsi.happyhour.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_cidade")
public class Cidade extends GenericDomain{
	@Column(length=40, nullable=false)
	private String nome;
	@JoinColumn(nullable=false)
	@ManyToOne
	private Estado estado;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
