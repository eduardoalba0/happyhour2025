package br.edu.ifpr.bsi.happyhour.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_servico")
public class Servico extends GenericDomain{
	@Column(length=30, nullable=false)
	private String descricao;
	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal valor;

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
}
