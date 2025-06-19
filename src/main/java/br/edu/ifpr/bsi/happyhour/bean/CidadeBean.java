package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.edu.ifpr.bsi.happyhour.dao.CidadeDAO;
import br.edu.ifpr.bsi.happyhour.dao.EstadoDAO;
import br.edu.ifpr.bsi.happyhour.dao.PaisDAO;
import br.edu.ifpr.bsi.happyhour.model.Cidade;
import br.edu.ifpr.bsi.happyhour.model.Estado;
import br.edu.ifpr.bsi.happyhour.model.Pais;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("cidadeBean")
@ViewScoped
public class CidadeBean implements Serializable {
	private Cidade cidade;
	private Pais pais;
	private List<Cidade> cidades;
	private List<Pais> paises;
	private List<Estado> estados;
	private boolean disEstado;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			CidadeDAO dao = new CidadeDAO();
			cidades = dao.listar();
			PaisDAO paisDAO = new PaisDAO();
			paises = paisDAO.listar();
			estados = new ArrayList<Estado>();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO dao = new CidadeDAO();
			dao.merge(cidade);
			novo();
			Messages.addGlobalInfo("Cidade salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar cidade.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
		pais = cidade.getEstado().getPais();
		filtraEstados();
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
		try {
			CidadeDAO dao = new CidadeDAO();
			dao.deletar(cidade);
			novo();
			Messages.addGlobalInfo("Cidade removida.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover cidade.");
			ex.printStackTrace();
		}
	}

	public void filtraEstados() {
		try {
			estados = new ArrayList<Estado>();
			disEstado = true;
			if (pais != null)
				if (pais.getCodigo() != null) {
					EstadoDAO dao = new EstadoDAO();
					estados = dao.listar("pais", "nome", pais);
					disEstado = false;
				}
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao filtrar estados.");
			ex.printStackTrace();
		}
	}

	public void ajaxFiltraEstados(AjaxBehaviorEvent event) {
		filtraEstados();
	}

	public void novo() {
		disEstado = true;
		cidade = new Cidade();
		cidade.setEstado(new Estado());
		cidade.getEstado().setPais(new Pais());
		collapsed = !collapsed;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public boolean isDisEstado() {
		return disEstado;
	}

	public void setDisEstado(boolean disEstado) {
		this.disEstado = disEstado;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
