package br.edu.ifpr.bsi.happyhour.dao;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Endereco;
import br.edu.ifpr.bsi.happyhour.model.Local;

public class LocalDAOTest {
	
	@Test
	public void merge() {
		Local local = new Local();
		local.setDescricaoLocal("Local Merge Test");
		local.setCapacidadeLocal(20);
		local.setValorLocacao(new BigDecimal(0));
	
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Merge Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
		local.setEndereco(endereco);
		
		LocalDAO dao = new LocalDAO();
		dao.merge(local);
		
		local = dao.listar("descricaoLocal", "descricaoLocal", "Local Merge Test").get(0);
		local.setDescricaoLocal("Teste OK");
		dao.merge(local);
	}

	@Test
	public void excluir() {
		Local local = new Local();
		local.setDescricaoLocal("Local Delete Test");
		local.setCapacidadeLocal(20);
		local.setValorLocacao(new BigDecimal(0));
	
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Merge Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
		local.setEndereco(endereco);

		LocalDAO dao = new LocalDAO();
		dao.merge(local);
		
		local = dao.listar("descricaoLocal", "descricaoLocal", "Local Delete Test").get(0);
		dao.deletar(local);
	}

	@Test
	public void listar() {
		LocalDAO dao = new LocalDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		LocalDAO dao = new LocalDAO();
		EnderecoDAO estDao = new EnderecoDAO();
		System.out.println(dao.listar("endereco", "descricaoLocal", estDao.listar(1L)));
	}

	@Test
	public void listarOrdenado() {
		LocalDAO dao = new LocalDAO();
		System.out.println(dao.listar("descricaoLocal"));
	}

	@Test
	public void get() {
		LocalDAO dao = new LocalDAO();
		System.out.println(dao.listar(1L).getDescricaoLocal());
	}
}
