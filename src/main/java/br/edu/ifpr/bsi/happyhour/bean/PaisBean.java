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

import br.edu.ifpr.bsi.happyhour.dao.PaisDAO;
import br.edu.ifpr.bsi.happyhour.model.Pais;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("paisBean")
@ViewScoped
public class PaisBean implements Serializable {
	private Pais pais;
	private List<Pais> paises;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			PaisDAO dao = new PaisDAO();
			paises = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PaisDAO dao = new PaisDAO();
			dao.merge(pais);
			novo();
			Messages.addGlobalInfo("País salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar país.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		pais = (Pais) evento.getComponent().getAttributes().get("paisSelecionado");
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		pais = (Pais) evento.getComponent().getAttributes().get("paisSelecionado");
		try {
			PaisDAO dao = new PaisDAO();
			dao.deletar(pais);
			novo();
			Messages.addGlobalInfo("País removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover país.");
			ex.printStackTrace();
		}
	}

	public void novo() {
		pais = new Pais();
		collapsed = !collapsed;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
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
