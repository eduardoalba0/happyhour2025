package br.edu.ifpr.bsi.happyhour.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "tb_evento")
public class Evento extends GenericDomain {
	@Column(length = 30, nullable = false)
	private String descricao;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEvento;
	@Column(nullable = false)
	private Byte duracao;
	@Column(nullable = false)
	private boolean publico;
	@Column(precision = 11, scale = 2)
	private BigDecimal custo;
	@JoinColumn
	
	@Transient
	private String dataEventoStr;

	@JoinColumn(nullable = false)
	@ManyToOne
	private Local local;

	@JoinColumn(nullable = false)
	@ManyToOne
	private Cliente cliente;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "evento", fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Entrada> entradas;

	@JoinTable(name = "servicosEvento", joinColumns = { @JoinColumn(name = "evento_codigo") }, inverseJoinColumns = {
			@JoinColumn(name = "servico_codigo") })
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Servico> servicos;

	@Transient
	public String getDataEventoStr() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy - HH:mm");
		dataEventoStr = format.format(dataEvento);
		return dataEventoStr;
	}

	@Transient
	public void calculaCusto() {
		custo = new BigDecimal(0);
		if (local != null && duracao != null) {
			custo = custo.add(local.getValorLocacao().multiply(BigDecimal.valueOf(duracao.doubleValue())));
		}
		if (servicos != null)
			if (!servicos.isEmpty())
				servicos.forEach((servico) -> {
					custo = custo.add(servico.getValor());
				});
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public Byte getDuracao() {
		return duracao;
	}

	public void setDuracao(Byte duracao) {
		this.duracao = duracao;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

}
