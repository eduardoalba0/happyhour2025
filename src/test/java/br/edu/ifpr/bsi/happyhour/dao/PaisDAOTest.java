package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Pais;

public class PaisDAOTest {
	
	@Test
	public void merge() {
		Pais pais = new Pais();
		pais.setNome("Teste Pais");
				
		PaisDAO dao = new PaisDAO();
		dao.merge(pais);
	}
	@Test
	public void excluir() {
		Pais pais = new Pais();
		pais.setNome("Pais Delete Test");

		PaisDAO dao = new PaisDAO();
		dao.merge(pais);
		
		pais = dao.listar("nome", "nome", "Pais Delete Test").get(0);
		dao.deletar(pais);
	}

	@Test
	public void listar() {
		PaisDAO dao = new PaisDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		PaisDAO dao = new PaisDAO();
		System.out.println(dao.listar("nome", "nome", "Brasil"));
	}

	@Test
	public void listarOrdenado() {
		PaisDAO dao = new PaisDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		PaisDAO dao = new PaisDAO();
		System.out.println(dao.listar(2L).getNome());
	}
}
