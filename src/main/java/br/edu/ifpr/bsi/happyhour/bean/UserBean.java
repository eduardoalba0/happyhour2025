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

import br.edu.ifpr.bsi.happyhour.dao.UsuarioDAO;
import br.edu.ifpr.bsi.happyhour.model.Usuario;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {
	private Usuario usuario;
	private List<Usuario> usuarios;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		novo();
		collapsed = true;
		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuarios = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao recuperar dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		if (autentica())
			try {
				UsuarioDAO dao = new UsuarioDAO();
				dao.merge(usuario);
				novo();
				Messages.addGlobalInfo("Usuário salvo com sucesso.");
				listar();
			} catch (RuntimeException ex) {
				Messages.addGlobalError("Falha ao cadastrar usuário.");
				ex.printStackTrace();
			}
	}

	public void editar(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		collapsed = false;
	}

	public void excluir(ActionEvent evento) {
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.deletar(usuario);
			novo();
			Messages.addGlobalInfo("Usuário removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover usuário.");
			ex.printStackTrace();
		}
	}

	public boolean autentica() throws RuntimeException {
		if (usuario != null)
			if (!usuario.getNome().equals("") && !usuario.getSenha().equals("")) {
				UsuarioDAO dao = new UsuarioDAO();
				if (dao.autenticar(usuario) != null)
					Messages.addGlobalError("Nome de usuário já está cadastrado. Selecione outro.");
				else
					return true;
			}
		return false;
	}

	public void novo() {
		usuario = new Usuario();
		usuario.setAtivo(true);
		collapsed = !collapsed;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
}
