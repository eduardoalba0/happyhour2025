package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Usuario;

public class UsuarioDAOTest {

	@Test
	public void merge() {
		Usuario usuario = new Usuario();
		usuario.setNome("TesteUsuario");
		usuario.setSenha("1234");
		usuario.setTipo("Teste");
		usuario.setAtivo(false);
		UsuarioDAO dao = new UsuarioDAO();
		dao.merge(usuario);
	}

	@Test
	public void excluir() {
		Usuario usuario = new Usuario();
		usuario.setNome("UsuarioTest");
		usuario.setSenha("1234");
		usuario.setTipo("Teste");
		usuario.setAtivo(false);
		UsuarioDAO dao = new UsuarioDAO();
		dao.merge(usuario);

		usuario = dao.listar("nome", "nome", "UsuarioTest").get(0);
		dao.deletar(usuario);
	}

	@Test
	public void listar() {
		UsuarioDAO dao = new UsuarioDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		UsuarioDAO dao = new UsuarioDAO();
		System.out.println(dao.listar("tipo", "nome", "Teste"));
	}

	@Test
	public void listarOrdenado() {
		UsuarioDAO dao = new UsuarioDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		UsuarioDAO dao = new UsuarioDAO();
		System.out.println(dao.listar(2L).getNome());
	}
}
