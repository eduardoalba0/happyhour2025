package br.edu.ifpr.bsi.happyhour.dao;


import br.edu.ifpr.bsi.happyhour.model.Cidade;
import org.junit.jupiter.api.Test;

public class CidadeDAOTest {

	@Test
	public void merge() {
		Cidade cidade = new Cidade();
		cidade.setNome("Cidade Merge Test");
	
		EstadoDAO estDao = new EstadoDAO();
		cidade.setEstado(estDao.listar(2L));
		
		CidadeDAO dao = new CidadeDAO();
		dao.merge(cidade);
		
		cidade = dao.listar("nome", "nome", "Cidade Merge Test").get(0);
		cidade.setNome("Teste OK");
		dao.merge(cidade);
	}

	@Test
	public void excluir() {
		Cidade cidade = new Cidade();
		cidade.setNome("Cidade Delete Test");
	
		EstadoDAO estDao = new EstadoDAO();
		cidade.setEstado(estDao.listar(2L));
		
		CidadeDAO dao = new CidadeDAO();
		dao.merge(cidade);
		
		cidade = dao.listar("nome", "nome", "Cidade Delete Test").get(0);
		dao.deletar(cidade);
	}

	@Test
	public void listar() {
		CidadeDAO dao = new CidadeDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		CidadeDAO dao = new CidadeDAO();
		EstadoDAO estDao = new EstadoDAO();
		System.out.println(dao.listar("estado", "nome", estDao.listar(1L)));
	}

	@Test
	public void listarOrdenado() {
		CidadeDAO dao = new CidadeDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		CidadeDAO dao = new CidadeDAO();
		System.out.println(dao.listar(1L).getNome());
	}
}
