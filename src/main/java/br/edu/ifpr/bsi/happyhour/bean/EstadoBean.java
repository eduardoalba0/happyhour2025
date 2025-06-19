package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.edu.ifpr.bsi.happyhour.dao.EstadoDAO;
import br.edu.ifpr.bsi.happyhour.model.Estado;
import br.edu.ifpr.bsi.happyhour.model.Pais;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("estadoBean")
@ViewScoped
public class EstadoBean implements Serializable {
	private Estado estado;
	private Pais pais;
	private List<Estado> estados;
	private List<Pais> paises;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			EstadoDAO dao = new EstadoDAO();
			estados = dao.listar("pais");
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EstadoDAO dao = new EstadoDAO();
			dao.merge(estado);
			novo();
			Messages.addGlobalInfo("Estado salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar estado.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		try {
			EstadoDAO dao = new EstadoDAO();
			dao.deletar(estado);
			novo();
			Messages.addGlobalInfo("Estado removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover estado.");
			ex.printStackTrace();
		}
	}

	public void novo() {
		estado = new Estado();
		estado.setPais(new Pais());
		pais = null;
		collapsed = !collapsed;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
