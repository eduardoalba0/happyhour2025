package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.edu.ifpr.bsi.happyhour.dao.ConvidadoDAO;
import br.edu.ifpr.bsi.happyhour.model.Contato;
import br.edu.ifpr.bsi.happyhour.model.Convidado;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named ("convidadoBean")
@ViewScoped
public class ConvidadoBean implements Serializable {
	private Convidado convidado;
	private List<Convidado> convidados;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			ConvidadoDAO dao = new ConvidadoDAO();
			convidados = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao listar Convidados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ConvidadoDAO dao = new ConvidadoDAO();
			dao.merge(convidado);
			novo();
			Messages.addGlobalInfo("Convidado salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar convidado.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		convidado = (Convidado) evento.getComponent().getAttributes().get("convidadoSelecionado");
		if (convidado.getContato() == null)
			convidado.setContato(new Contato());
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		convidado = (Convidado) evento.getComponent().getAttributes().get("convidadoSelecionado");
		try {
			ConvidadoDAO dao = new ConvidadoDAO();
			dao.deletar(convidado);
			novo();
			Messages.addGlobalInfo("Convidado removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover convidado.");
			ex.printStackTrace();
		}
	}

	public void novo() {
		convidado = new Convidado();
		convidado.setContato(new Contato());
		collapsed = !collapsed;
	}

	public Convidado getConvidado() {
		return convidado;
	}

	public void setConvidado(Convidado convidado) {
		this.convidado = convidado;
	}

	public List<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}


}
