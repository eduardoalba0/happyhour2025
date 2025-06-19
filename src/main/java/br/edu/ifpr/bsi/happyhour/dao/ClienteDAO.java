package br.edu.ifpr.bsi.happyhour.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import br.edu.ifpr.bsi.happyhour.model.Cliente;
import br.edu.ifpr.bsi.happyhour.model.Usuario;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

import java.util.List;

public class ClienteDAO extends GenericDAO<Cliente> {

    public boolean docDisponivel(Cliente cliente) {
        List<Cliente> resultado;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
            Root<Cliente> root = criteria.from(Cliente.class);
            criteria.select(root).where(builder.and(
                    builder.equal(root.get("documento"), cliente.getDocumento()),
                    builder.notEqual(root.get("codigo"), cliente.getCodigo())
            ));
            resultado = session.createQuery(criteria).getResultList();
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            session.close();
        }
        return resultado == null || resultado.isEmpty();
    }

    public Cliente buscaUsuario(Usuario usuario) {
        List<Cliente> resultado;
        Cliente cliente = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
            Root<Cliente> root = criteria.from(Cliente.class);
            criteria.select(root).where(builder.equal(root.get("usuario"), usuario));
            resultado = session.createQuery(criteria).getResultList();
            if (resultado != null && !resultado.isEmpty()) {
                cliente = resultado.get(0);
            }
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            session.close();
        }
        return cliente;
    }
}
