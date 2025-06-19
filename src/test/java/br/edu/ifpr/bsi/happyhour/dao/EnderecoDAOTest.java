package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Endereco;

public class EnderecoDAOTest {
	
	@Test
	public void merge() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Merge Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
		
		CidadeDAO cidDao = new CidadeDAO();
		endereco.setCidade(cidDao.listar(2L));
		
		EnderecoDAO dao = new EnderecoDAO();
		dao.merge(endereco);
		
		endereco = dao.listar("logradouro", "logradouro", "Endereco Merge Test").get(0);
		endereco.setLogradouro("Teste OK");
		dao.merge(endereco);
	}

	@Test
	public void excluir() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Delete Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
	
		CidadeDAO cidDao = new CidadeDAO();
		endereco.setCidade(cidDao.listar(2L));
		
		EnderecoDAO dao = new EnderecoDAO();
		dao.merge(endereco);
		
		endereco = dao.listar("logradouro", "logradouro", "Endereco Delete Test").get(0);
		dao.deletar(endereco);
	}

	@Test
	public void listar() {
		EnderecoDAO dao = new EnderecoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		EnderecoDAO dao = new EnderecoDAO();
		CidadeDAO cidDao = new CidadeDAO();
		System.out.println(dao.listar("cidade", "logradouro", cidDao.listar(1L)));
	}

	@Test
	public void listarOrdenado() {
		EnderecoDAO dao = new EnderecoDAO();
		System.out.println(dao.listar("logradouro"));
	}

	@Test
	public void get() {
		EnderecoDAO dao = new EnderecoDAO();
		System.out.println(dao.listar(1L).getLogradouro());
	}
}
