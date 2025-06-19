package br.edu.ifpr.bsi.happyhour.dao;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Servico;

public class ServicoDAOTest {

	@Test
	public void merge() {
		Servico servico = new Servico();
		servico.setDescricao("ServicoDAOTeste");
		servico.setValor(new BigDecimal(0));
		
		ServicoDAO dao = new ServicoDAO();
		dao.merge(servico);
	}

	@Test
	public void excluir() {
		Servico servico = new Servico();
		servico.setDescricao("ServicoDAOTeste");
		servico.setValor(new BigDecimal(0));
		
		ServicoDAO dao = new ServicoDAO();
		dao.merge(servico);

		servico = dao.listar("descricao", "descricao", "ServicoDAOTeste").get(0);
		dao.deletar(servico);
	}

	@Test
	public void listar() {
		ServicoDAO dao = new ServicoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		ServicoDAO dao = new ServicoDAO();
		System.out.println(dao.listar("descricao", "descricao", "ServicoDAOTeste"));
	}

	@Test
	public void listarOrdenado() {
		ServicoDAO dao = new ServicoDAO();
		System.out.println(dao.listar("descricao"));
	}

	@Test
	public void get() {
		ServicoDAO dao = new ServicoDAO();
		System.out.println(dao.listar(2L).getDescricao());
	}
}
