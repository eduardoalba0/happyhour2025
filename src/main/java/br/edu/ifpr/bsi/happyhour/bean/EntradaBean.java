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

import br.edu.ifpr.bsi.happyhour.dao.EntradaDAO;
import br.edu.ifpr.bsi.happyhour.model.Entrada;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named ("entradaBean")
@ViewScoped
public class EntradaBean implements Serializable {
	private Entrada entrada;
	private List<Entrada> entradas;

	@PostConstruct
	public void listar() {
		try {
			EntradaDAO dao = new EntradaDAO();
			entradas = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EntradaDAO dao = new EntradaDAO();
			dao.merge(entrada);
			novo();
			Messages.addGlobalInfo("Tipo de entrada salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar Tipo de entrada.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		entrada = (Entrada) evento.getComponent().getAttributes().get("entradaSelecionada");
	}

	public void excluir(ActionEvent evento) {
		entrada = (Entrada) evento.getComponent().getAttributes().get("entradaSelecionada");
		try {
			EntradaDAO dao = new EntradaDAO();
			dao.deletar(entrada);
			novo();
			Messages.addGlobalInfo("Tipo de entrada removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover tipo de entrada.");
			ex.printStackTrace();
		}
	}

	public void novo() {
		entrada = new Entrada();
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}
}
