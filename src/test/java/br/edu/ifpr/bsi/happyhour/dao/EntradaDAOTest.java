package br.edu.ifpr.bsi.happyhour.dao;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Entrada;

public class EntradaDAOTest {
	
	@Test
	public void merge() {
		Entrada entrada = new Entrada();
		entrada.setDescricao("EntradaDAOTeste");
		entrada.setValor(new BigDecimal(0));
		
		EntradaDAO dao = new EntradaDAO();
		dao.merge(entrada);
	}

	@Test
	public void excluir() {
		Entrada entrada = new Entrada();
		entrada.setDescricao("EntradaDAOTeste");
		entrada.setValor(new BigDecimal(0));
		
		EntradaDAO dao = new EntradaDAO();
		dao.merge(entrada);

		entrada = dao.listar("descricao", "descricao", "EntradaDAOTeste").get(0);
		dao.deletar(entrada);
	}

	@Test
	public void listar() {
		EntradaDAO dao = new EntradaDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		EntradaDAO dao = new EntradaDAO();
		System.out.println(dao.listar("descricao", "descricao", "EntradaDAOTeste"));
	}

	@Test
	public void listarOrdenado() {
		EntradaDAO dao = new EntradaDAO();
		System.out.println(dao.listar("descricao"));
	}

	@Test
	public void get() {
		EntradaDAO dao = new EntradaDAO();
		System.out.println(dao.listar(1L).getDescricao());
	}
}
