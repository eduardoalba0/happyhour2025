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
import br.edu.ifpr.bsi.happyhour.dao.ClienteDAO;
import br.edu.ifpr.bsi.happyhour.dao.EstadoDAO;
import br.edu.ifpr.bsi.happyhour.dao.PaisDAO;
import br.edu.ifpr.bsi.happyhour.dao.UsuarioDAO;
import br.edu.ifpr.bsi.happyhour.model.Cidade;
import br.edu.ifpr.bsi.happyhour.model.Cliente;
import br.edu.ifpr.bsi.happyhour.model.Contato;
import br.edu.ifpr.bsi.happyhour.model.Endereco;
import br.edu.ifpr.bsi.happyhour.model.Estado;
import br.edu.ifpr.bsi.happyhour.model.Pais;
import br.edu.ifpr.bsi.happyhour.model.Usuario;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named ("clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {
	private Cliente cliente;
	private List<Cliente> clientes;
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
			ClienteDAO dao = new ClienteDAO();
			clientes = dao.listar();
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
		if (autentica())
			try {
				ClienteDAO dao = new ClienteDAO();
				dao.merge(cliente);
				novo();
				Messages.addGlobalInfo("Cliente salvo com sucesso.");
				listar();
			} catch (RuntimeException ex) {
				Messages.addGlobalError("Falha ao salvar cliente.");
				ex.printStackTrace();
			}
	}

	public void editar(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		pais = cliente.getEndereco().getCidade().getEstado().getPais();
		estado = cliente.getEndereco().getCidade().getEstado();
		filtraEstados();
		filtraCidades();
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		try {
			ClienteDAO dao = new ClienteDAO();
			dao.deletar(cliente);
			novo();
			Messages.addGlobalInfo("Cliente removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError(
					"Falha ao remover cliente. Verifique se ele está associado à algum evento e tente novamente.");
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
		if (cliente.getEndereco().getCidade() != null)
			cliente.getEndereco().getCidade().setCodigo(null);
	}
	
	public boolean autentica() throws RuntimeException {
		if (cliente.getUsuario() != null)
			if (!cliente.getUsuario().getNome().equals("") && !cliente.getUsuario().getSenha().equals("")) {
				UsuarioDAO dao = new UsuarioDAO();
				if (dao.autenticar(cliente.getUsuario()) != null)
					Messages.addGlobalError("Nome de usuário já está cadastrado. Selecione outro.");
				else
					return true;
			}
		return false;
	}

	public void novo() {
		disCidade = true;
		disEstado = true;
		cliente = new Cliente();
		cliente.setEndereco(new Endereco());
		cliente.setContato(new Contato());
		Usuario usuario = new Usuario();
		usuario.setTipo("Cliente");
		usuario.setAtivo(true);
		cliente.setUsuario(usuario);
		pais = new Pais();
		estado = new Estado();
		collapsed = !collapsed;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
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

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
