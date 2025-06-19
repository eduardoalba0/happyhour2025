package br.edu.ifpr.bsi.happyhour.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import br.edu.ifpr.bsi.happyhour.model.Evento;

public class EventoDAOTest {
	
	@Test
	public void merge() {
		Evento evento = new Evento();
		evento.setDescricao("Teste Evento");
		evento.setDataEvento(new Date());
		evento.setDuracao((byte)5);
		evento.setPublico(false);
		evento.setCusto(new BigDecimal(0));
		
		LocalDAO locDao = new LocalDAO();
		evento.setLocal(locDao.listar().get(0));
		ClienteDAO cliDao = new ClienteDAO();
		evento.setCliente(cliDao.listar().get(0));
		EventoDAO dao = new EventoDAO();
		dao.merge(evento);

		evento = dao.listar("descricao", "descricao", evento.getDescricao()).get(0);
		evento.setDescricao("Teste OK");
		dao.merge(evento);
	}
	@Test
	public void excluir() {
		Evento evento = new Evento();
		evento.setDescricao("Evento Delete Test");
		evento.setDataEvento(new Date());
		evento.setDuracao((byte)5);
		evento.setPublico(false);
		evento.setCusto(new BigDecimal(0));
		
		LocalDAO locDao = new LocalDAO();
		evento.setLocal(locDao.listar().get(0));
		ClienteDAO cliDao = new ClienteDAO();
		evento.setCliente(cliDao.listar().get(0));

		EventoDAO dao = new EventoDAO();
		dao.merge(evento);
		
		evento = dao.listar("descricao", "descricao", "Evento Delete Test").get(0);
		dao.deletar(evento);
	}

	@Test
	public void listar() {
		EventoDAO dao = new EventoDAO();
		System.out.println(dao.listar());
	}

	@Test
	public void listarFiltro() {
		EventoDAO dao = new EventoDAO();
		System.out.println(dao.listar("publico", "descricao", true));
	}

	@Test
	public void listarOrdenado() {
		EventoDAO dao = new EventoDAO();
		System.out.println(dao.listar("descricao"));
	}

	@Test
	public void get() {
		EventoDAO dao = new EventoDAO();
		System.out.println(dao.listar(3L).getDescricao());
	}
}
