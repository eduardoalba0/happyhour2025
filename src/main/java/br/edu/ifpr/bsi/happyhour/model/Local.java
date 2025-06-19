package br.edu.ifpr.bsi.happyhour.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_local")
public class Local extends GenericDomain {
	@Column(length=30, nullable=false)
	private String descricaoLocal;
	@Column(nullable=false)
	private int capacidadeLocal;
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal valorLocacao;
	@JoinColumn
	@OneToOne(cascade = {CascadeType.ALL})
	private Endereco endereco;
	public String getDescricaoLocal() {
		return descricaoLocal;
	}
	public void setDescricaoLocal(String descricaoLocal) {
		this.descricaoLocal = descricaoLocal;
	}
	public int getCapacidadeLocal() {
		return capacidadeLocal;
	}
	public void setCapacidadeLocal(int capacidadeLocal) {
		this.capacidadeLocal = capacidadeLocal;
	}
	public BigDecimal getValorLocacao() {
		return valorLocacao;
	}
	public void setValorLocacao(BigDecimal valorLocacao) {
		this.valorLocacao = valorLocacao;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
