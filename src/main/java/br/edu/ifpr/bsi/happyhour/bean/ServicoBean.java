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

import br.edu.ifpr.bsi.happyhour.dao.ServicoDAO;
import br.edu.ifpr.bsi.happyhour.model.Servico;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("servicoBean")
@ViewScoped
public class ServicoBean implements Serializable {
	private Servico servico;
	private List<Servico> servicos;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			ServicoDAO dao = new ServicoDAO();
			servicos = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ServicoDAO dao = new ServicoDAO();
			dao.merge(servico);
			novo();
			Messages.addGlobalInfo("Servico salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar servico.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		servico = (Servico) evento.getComponent().getAttributes().get("servicoSelecionado");
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		servico = (Servico) evento.getComponent().getAttributes().get("servicoSelecionado");
		try {
			ServicoDAO dao = new ServicoDAO();
			dao.deletar(servico);
			novo();
			Messages.addGlobalInfo("Servico removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover servico.");
			ex.printStackTrace();
		}
	}

	public void novo() {
		servico = new Servico();
		collapsed = !collapsed;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
