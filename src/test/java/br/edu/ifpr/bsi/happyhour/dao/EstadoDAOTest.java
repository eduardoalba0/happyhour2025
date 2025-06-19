package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Estado;

public class EstadoDAOTest {

	@Test
	public void merge() {
		Estado estado = new Estado();
		estado.setNome("Teste Estado");
		estado.setSigla("TE");
		
		PaisDAO paisD = new PaisDAO();
		estado.setPais(paisD.listar(1L));
		
		EstadoDAO dao = new EstadoDAO();
		dao.merge(estado);
	}
	@Test
	public void excluir() {
		Estado estado = new Estado();
		estado.setNome("Estado Delete Test");
		estado.setSigla("ET");
		PaisDAO paisDao = new PaisDAO();
		estado.setPais(paisDao.listar(2L));
		
		EstadoDAO dao = new EstadoDAO();
		dao.merge(estado);
		
		estado = dao.listar("nome", "nome", "Estado Delete Test").get(0);
		dao.deletar(estado);
	}

	@Test
	public void listar() {
		EstadoDAO dao = new EstadoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		EstadoDAO dao = new EstadoDAO();
		PaisDAO paisDao = new PaisDAO();
		System.out.println(dao.listar("pais", "nome", paisDao.listar(1L)));
	}

	@Test
	public void listarOrdenado() {
		EstadoDAO dao = new EstadoDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		EstadoDAO dao = new EstadoDAO();
		System.out.println(dao.listar(1L).getNome());
	}
}
