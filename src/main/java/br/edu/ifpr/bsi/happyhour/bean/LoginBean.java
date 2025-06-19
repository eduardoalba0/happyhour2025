package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.omnifaces.util.Messages;

import br.edu.ifpr.bsi.happyhour.dao.UsuarioDAO;
import br.edu.ifpr.bsi.happyhour.model.Usuario;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	private Usuario usuarioLogado;
	private Usuario usuario;
	private boolean admin, cliente, atendente;

	@PostConstruct
	public void novo() {
		usuario = new Usuario();
		admin = false;
		cliente = false;
		atendente = false;
	}

	public void entrar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			usuarioLogado = dao.autenticar(usuario);
			if (usuarioLogado != null) {
				Messages.addFlashGlobalInfo("Bem vindo, " + usuarioLogado.getNome());
				switch (usuarioLogado.getTipo()) {
				case "Administrador":
					admin = true;
					atendente = true;
					break;
				case "Atendente":
					atendente = true;
					break;
				case "Cliente":
					cliente = true;
				}
			} else {
				Messages.addGlobalError("Nome de usuário, ou senha inválidos!");
			}
			usuario = new Usuario();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Ocorreu um erro na aplicação. Tente novamente mais tarde.");
			ex.printStackTrace();
		}
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void sair() {
		usuarioLogado = null;
		novo();
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public boolean isAtendente() {
		return atendente;
	}

	public void setAtendente(boolean atendente) {
		this.atendente = atendente;
	}

}
