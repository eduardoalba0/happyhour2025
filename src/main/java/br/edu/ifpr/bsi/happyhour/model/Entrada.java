package br.edu.ifpr.bsi.happyhour.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_entrada")
public class Entrada extends GenericDomain {
	@Column(length = 30, nullable = false)
	private String descricao;
	@Column(nullable = false, precision = 11, scale = 2)
	private BigDecimal valor;
	@JoinColumn
	@ManyToOne
	private Evento evento;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
