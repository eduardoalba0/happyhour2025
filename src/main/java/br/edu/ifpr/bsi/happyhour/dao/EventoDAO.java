package br.edu.ifpr.bsi.happyhour.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import br.edu.ifpr.bsi.happyhour.model.Evento;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

public class EventoDAO extends GenericDAO<Evento> {

	public boolean horarioDisponivel(Evento evento) {
		List<Evento> resultado;
		Session session = HibernateHelper.getFabricaDeSessoes().openSession();
		try {


			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dFinal = new Date(evento.getDataEvento().getTime()+((evento.getDuracao().longValue())*3600000));

			String hql = "SELECT 1 FROM Evento e " +
					"WHERE e.local = :local " +
					"AND e.codigo != :codigo " +
					"AND ( " +
					"  (e.dataEvento < :inicio AND (e.dataEvento + e.duracao * 3600000) > :inicio) " +
					"  OR (e.dataEvento < :fim AND (e.dataEvento + e.duracao * 3600000) > :fim) " +
					"  OR (e.dataEvento < :inicio AND (e.dataEvento + e.duracao * 3600000) > :fim) " +
					")";

			Query query = session.createQuery(hql);
			query.setParameter("local", evento.getLocal());
			query.setParameter("codigo", evento.getCodigo());
			query.setParameter("inicio", evento.getDataEvento());
			query.setParameter("fim", dFinal);
			resultado = query.getResultList();
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
		return resultado == null || resultado.isEmpty();
	}

	public Long getCodigo(Evento evento) {
		List<Evento> resultado;
		Evento eventoResultado = null;
		Session session = HibernateHelper.getFabricaDeSessoes().openSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Evento> criteria = builder.createQuery(Evento.class);
			Root<Evento> root = criteria.from(Evento.class);
			criteria.select(root).where(builder.and(
					builder.equal(root.get("descricao"), evento.getDescricao()),
					builder.equal(root.get("local"), evento.getLocal()),
					builder.equal(root.get("dataEvento"), evento.getDataEvento())
			));
			resultado = session.createQuery(criteria).getResultList();
			if (resultado != null && !resultado.isEmpty()) {
				eventoResultado = resultado.get(0);
			}
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
		if (eventoResultado != null)
			return eventoResultado.getCodigo();
		else
			return null;
	}

}
