package br.edu.ifpr.bsi.happyhour.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_cliente")
public class Cliente extends GenericDomain {

	@Column(length = 30, nullable = false)
	private String nome;

	@Column(length = 10, nullable = false)
	private String tipoPessoa;
	
	@Column(length = 20, nullable = false)
	private String documento;
	
	@JoinColumn
	@OneToOne(cascade = {CascadeType.ALL})
	private Contato contato;
	
	@JoinColumn
	@OneToOne(cascade = {CascadeType.ALL})
	private Endereco endereco;
	
	@JoinColumn(nullable = false)
	@OneToOne(cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
