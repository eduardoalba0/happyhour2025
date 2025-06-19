package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;


import br.edu.ifpr.bsi.happyhour.dao.CidadeDAO;
import br.edu.ifpr.bsi.happyhour.dao.EstadoDAO;
import br.edu.ifpr.bsi.happyhour.dao.LocalDAO;
import br.edu.ifpr.bsi.happyhour.dao.PaisDAO;
import br.edu.ifpr.bsi.happyhour.model.Cidade;
import br.edu.ifpr.bsi.happyhour.model.Endereco;
import br.edu.ifpr.bsi.happyhour.model.Estado;
import br.edu.ifpr.bsi.happyhour.model.Local;
import br.edu.ifpr.bsi.happyhour.model.Pais;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("localBean")
@ViewScoped
public class LocalBean implements Serializable {
	private Local local;
	private List<Local> locais;
	private List<Pais> paises;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private Estado estado;
	private Pais pais;
	private boolean disEstado, disCidade, collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			LocalDAO dao = new LocalDAO();
			locais = dao.listar();
			PaisDAO paisDAO = new PaisDAO();
			paises = paisDAO.listar();
			estados = new ArrayList<Estado>();
			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			LocalDAO dao = new LocalDAO();
			dao.merge(local);
			novo();
			Messages.addGlobalInfo("Local salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar local.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		local = (Local) evento.getComponent().getAttributes().get("localSelecionado");
		pais = local.getEndereco().getCidade().getEstado().getPais();
		estado = local.getEndereco().getCidade().getEstado();
		filtraEstados();
		filtraCidades();
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		local = (Local) evento.getComponent().getAttributes().get("localSelecionado");
		try {
			LocalDAO dao = new LocalDAO();
			dao.deletar(local);
			novo();
			Messages.addGlobalInfo("Local removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover local.");
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

	public void filtraCidades() {
		try {
			cidades = new ArrayList<Cidade>();
			disCidade = true;
			if (estado != null)
				if (estado.getCodigo() != null) {
					CidadeDAO dao = new CidadeDAO();
					cidades = dao.listar("estado", "nome", estado);
					disCidade = false;
				}
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao filtrar cidades.");
			ex.printStackTrace();
		}
	}

	public void ajaxFiltraEstados(AjaxBehaviorEvent event) {
		filtraEstados();
		estado.setCodigo(null);
		filtraCidades();
	}

	public void ajaxFiltraCidades(AjaxBehaviorEvent event) {
		filtraCidades();
		if (local.getEndereco().getCidade() != null)
			local.getEndereco().getCidade().setCodigo(null);
	}

	public void novo() {
		disCidade = true;
		disEstado = true;
		local = new Local();
		local.setEndereco(new Endereco());
		pais = new Pais();
		estado = new Estado();
		collapsed = !collapsed;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public List<Local> getLocais() {
		return locais;
	}

	public void setLocais(List<Local> locais) {
		this.locais = locais;
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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
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

	public boolean isDisEstado() {
		return disEstado;
	}

	public void setDisEstado(boolean disEstado) {
		this.disEstado = disEstado;
	}

	public boolean isDisCidade() {
		return disCidade;
	}

	public void setDisCidade(boolean disCidade) {
		this.disCidade = disCidade;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
