package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Contato;
import br.edu.ifpr.bsi.happyhour.model.Convidado;

public class ConvidadoDAOTest {
	
	@Test
	public void merge() {
		Convidado convidado = new Convidado();
		convidado.setNome("Convidado Merge Test");
	
		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("mergetest@contatoDAO.com");
		convidado.setContato(contato);
		
		ConvidadoDAO dao = new ConvidadoDAO();
		dao.merge(convidado);
		
		convidado = dao.listar("nome", "nome", "Convidado Merge Test").get(0);
		convidado.setNome("Teste OK");
		dao.merge(convidado);
	}

	@Test
	public void excluir() {
		Convidado convidado = new Convidado();
		convidado.setNome("ConvidadoDeleteTest");
	
		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("mergetest@contatoDAO.com");
		convidado.setContato(contato);
		
		ConvidadoDAO dao = new ConvidadoDAO();
		dao.merge(convidado);
		
		convidado = dao.listar("nome", "nome", "ConvidadoDeleteTest").get(0);
		dao.deletar(convidado);
	}

	@Test
	public void listar() {
		ConvidadoDAO dao = new ConvidadoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		ConvidadoDAO dao = new ConvidadoDAO();
		ContatoDAO conDao = new ContatoDAO();
		System.out.println(dao.listar("contato", "nome", conDao.listar(1L)));
	}

	@Test
	public void listarOrdenado() {
		ConvidadoDAO dao = new ConvidadoDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		ConvidadoDAO dao = new ConvidadoDAO();
		System.out.println(dao.listar(1L).getNome());
	}
}
