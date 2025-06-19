package br.edu.ifpr.bsi.happyhour.dao;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Endereco;
import br.edu.ifpr.bsi.happyhour.model.Usuario;
import br.edu.ifpr.bsi.happyhour.model.Cliente;
import br.edu.ifpr.bsi.happyhour.model.Contato;

public class ClienteDAOTest {

	@Test
	public void merge() {
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Merge Test");
		cliente.setDocumento("test");
		cliente.setTipoPessoa("Física");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Merge Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
		cliente.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("mergetest@contatoDAO.com");
		cliente.setContato(contato);
		
		Usuario usuario = new Usuario();
		usuario.setNome("TesteCliente");
		usuario.setSenha("1234");
		usuario.setTipo("Teste");
		usuario.setAtivo(false);
		cliente.setUsuario(usuario);
		
		ClienteDAO dao = new ClienteDAO();
		dao.merge(cliente);

		cliente = dao.listar("nome", "nome", "Cliente Merge Test").get(0);
		cliente.setNome("Teste OK");
		dao.merge(cliente);
	}

	@Test
	public void excluir() {
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Delete Test");
		cliente.setDocumento("test");
		cliente.setTipoPessoa("Física");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Endereco Merge Test");
		endereco.setNumero("11");
		endereco.setBairro("EnderecoDAO");
		endereco.setCep("00000-000");
		cliente.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setTelefone("(46)3262-6835");
		contato.setCelular("(46)99105-4638");
		contato.setEmail("mergetest@contatoDAO.com");
		cliente.setContato(contato);

		Usuario usuario = new Usuario();
		usuario.setNome("TesteCliente");
		usuario.setSenha("1234");
		usuario.setTipo("Teste");
		usuario.setAtivo(false);
		cliente.setUsuario(usuario);

		ClienteDAO dao = new ClienteDAO();
		dao.merge(cliente);

		cliente = dao.listar("nome", "nome", "Cliente Delete Test").get(0);
		dao.deletar(cliente);
	}

	@Test
	public void listar() {
		ClienteDAO dao = new ClienteDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		ClienteDAO dao = new ClienteDAO();
		System.out.println(dao.listar("tipoPessoa", "nome", "Física"));
	}

	@Test
	public void listarOrdenado() {
		ClienteDAO dao = new ClienteDAO();
		System.out.println(dao.listar("nome"));
	}

	@Test
	public void get() {
		ClienteDAO dao = new ClienteDAO();
		System.out.println(dao.listar(1L).getNome());
	}
}
