package br.edu.ifpr.bsi.happyhour.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.event.ActionEvent;

import org.omnifaces.util.Messages;


import br.edu.ifpr.bsi.happyhour.dao.ClienteDAO;
import br.edu.ifpr.bsi.happyhour.dao.EntradaDAO;
import br.edu.ifpr.bsi.happyhour.dao.EventoDAO;
import br.edu.ifpr.bsi.happyhour.dao.ServicoDAO;
import br.edu.ifpr.bsi.happyhour.model.Cliente;
import br.edu.ifpr.bsi.happyhour.model.Entrada;
import br.edu.ifpr.bsi.happyhour.model.Evento;
import br.edu.ifpr.bsi.happyhour.model.Servico;

@Named("eventoBean")
@ViewScoped
public class EventoBean implements Serializable {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	private Cliente cliente;
	private Evento evento;
	private Entrada entrada;
	private Servico servico;
	private List<Evento> eventos, index;
	private Date horario, data;
	private boolean collapsed;

	@PostConstruct
	public void listar() {
		collapsed = true;
		try {
			EventoDAO dao = new EventoDAO();
			index = dao.listar("publico", "dataEvento", true);
//			if (loginBean != null)
//				if (loginBean.getUsuarioLogado() != null)
//					if (loginBean.isCliente()) {
//						ClienteDAO cliDAO = new ClienteDAO();
//						cliente = cliDAO.buscaUsuario(loginBean.getUsuarioLogado());
//						if (cliente != null)
//							eventos = dao.listar("cliente", "dataEvento", cliente);
//					} else if (loginBean.isAtendente())
						eventos = dao.listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao consultar o banco de dados.");
			ex.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EventoDAO dao = new EventoDAO();
			evento.calculaCusto();
			if (loginBean!= null && loginBean.getUsuarioLogado() != null)
				if (loginBean.isCliente())
					evento.setCliente(cliente);
			dao.merge(evento);
			Messages.addGlobalInfo("Evento salvo com sucesso.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao salvar evento.");
			ex.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
		entrada = new Entrada();
		servico = new Servico();
		collapsed = false;
	}

	public void excluir(ActionEvent event) {
		evento = (Evento) event.getComponent().getAttributes().get("eventoSelecionado");
		try {
			EventoDAO dao = new EventoDAO();
			dao.deletar(evento);
			novo();
			Messages.addGlobalInfo("Evento removido.");
			listar();
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao remover evento.");
			ex.printStackTrace();
		}
	}

	public void addEntrada() {
		try {
			if (entrada != null && evento != null)
				if (evento.getCodigo() == null) {
					salvar();
					EventoDAO daoEvento = new EventoDAO();
					evento.setCodigo(daoEvento.getCodigo(evento));
				}
			if (evento.getCodigo() != null && !entrada.getDescricao().equals("")) {
				if (entrada.getValor() == null)
					entrada.setValor(new BigDecimal(0));
				EntradaDAO dao = new EntradaDAO();
				entrada.setEvento(evento);
				dao.merge(entrada);
				entrada = new Entrada();
				atualiza();
			}
		} catch (RuntimeException ex) {
			Messages.addGlobalError("Falha ao acicionar tipo de entrada.");
			ex.printStackTrace();
		}
	}

	public void editarEntrada(ActionEvent event) {
		entrada = (Entrada) event.getComponent().getAttributes().get("entradaSelecionada");
	}

	public void excluirEntrada(ActionEvent event) {
		entrada = (Entrada) event.getComponent().getAttributes().get("entradaSelecionada");
		try {
			EntradaDAO dao = new EntradaDAO();
			dao.deletar(entrada);
			entrada = new Entrada();
			atualiza();
		} catch (RuntimeException ex) {
			Messages.addGlobalError(
					"Falha ao remover o tipo de entrada.");
			ex.printStackTrace();
		}
	}

	public void addServico() {
		if (servico != null) {
			if (!servico.getDescricao().equals("")) {
				if ((servico.getCodigo().equals(19L) || servico.getCodigo().equals(20L))) {
					Messages.addGlobalError("Este serviço não pode ser incluído manualmente.");
					return;
				}
				evento.getServicos().add(servico);
				servico = new Servico();
			} else
				Messages.addGlobalError("Selecione um serviço.");
		} else
			Messages.addGlobalError("Selecione um serviço.");
	}

	public void excluirServico() {
		int i = 0;
		for (Servico serv : evento.getServicos()) {
			if (servico.getCodigo().equals(serv.getCodigo()))
				break;
			i++;
		}
		evento.getServicos().remove(i);
		servico = new Servico();
	}

	public void excluirServico(ActionEvent event) {
		servico = (Servico) event.getComponent().getAttributes().get("servicoSelecionado");
		if ((servico.getCodigo().equals(19L) || servico.getCodigo().equals(20L)))
			Messages.addGlobalError("Este Serviço não pode ser manualmente.");
		else
			excluirServico();
	}

	public void atualiza() throws RuntimeException {
		EntradaDAO dao = new EntradaDAO();
		if (evento != null)
			if (evento.getCodigo() != null)
				evento.setEntradas(dao.listar("evento", "descricao", evento));
	}

	public void salvar(ActionEvent event) {
		salvar();
		novo();
	}
	public boolean containsServico(Long id) {
		if (evento != null)
			if (evento.getServicos() != null)
				for (Servico serv : evento.getServicos()) {
					if (serv.getCodigo().equals(id))
						return true;
				}
		return false;
	}
	public void novo() {
		evento = new Evento();
		evento.setDuracao((byte) 0);
		evento.setEntradas(new ArrayList<Entrada>());
		evento.setServicos(new ArrayList<Servico>());
		entrada = new Entrada();
		data = null;
		horario = null;
		collapsed = !collapsed;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Evento> getIndex() {
		return index;
	}

	public void setIndex(List<Evento> index) {
		this.index = index;
	}

}
