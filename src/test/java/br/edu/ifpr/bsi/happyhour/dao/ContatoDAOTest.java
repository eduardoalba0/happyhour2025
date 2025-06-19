package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Contato;

public class ContatoDAOTest {

	@Test
	public void merge() {
		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("mergetest@contatoDAO.com");
		ContatoDAO dao = new ContatoDAO();
		dao.merge(contato);
	}

	@Test
	public void excluir() {
		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("deletetest@contatoDAO.com");

		ContatoDAO dao = new ContatoDAO();
		dao.merge(contato);

		contato = dao.listar("email", "email", "deletetest@contatoDAO.com").get(0);
		dao.deletar(contato);
	}

	@Test
	public void listar() {
		ContatoDAO dao = new ContatoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		ContatoDAO dao = new ContatoDAO();
		System.out.println(dao.listar("email", "email", "mergetest@contatoDAO.com"));
	}

	@Test
	public void listarOrdenado() {
		ContatoDAO dao = new ContatoDAO();
		System.out.println(dao.listar("email"));
	}

	@Test
	public void get() {
		ContatoDAO dao = new ContatoDAO();
		System.out.println(dao.listar(1L).getEmail());
	}
}
