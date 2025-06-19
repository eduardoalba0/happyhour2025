package br.edu.ifpr.bsi.happyhour.dao;

import br.edu.ifpr.bsi.happyhour.model.Cliente;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import br.edu.ifpr.bsi.happyhour.model.Usuario;
import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

import java.util.List;

public class UsuarioDAO extends GenericDAO<Usuario> {
    public Usuario autenticar(Usuario usuario) {
        List<Usuario> resultado;
        Usuario usuarioResultado = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
            Root<Usuario> root = criteria.from(Usuario.class);
            criteria.select(root).where(builder.and(
                    builder.equal(root.get("nome"), usuario.getNome()),
                    builder.equal(root.get("senha"), usuario.getSenha()))
            );

            if (usuario.getCodigo() == null)
                criteria.select(root).where(builder.and(
                        builder.equal(root.get("nome"), usuario.getNome()),
                        builder.equal(root.get("senha"), usuario.getSenha()))
                );
            else
                criteria.select(root).where(builder.and(
                        builder.equal(root.get("nome"), usuario.getNome()),
                        builder.equal(root.get("senha"), usuario.getSenha()),
                        builder.equal(root.get("codigo"), usuario.getCodigo()))
                );

            resultado = session.createQuery(criteria).getResultList();
            if (resultado != null && !resultado.isEmpty()) {
                usuarioResultado = resultado.get(0);
            }
        } catch (RuntimeException erro) {
            System.out.println("Ocorreu um erro ao buscar a entidade.");
            throw erro;
        } finally {
            session.close();
        }
        return usuarioResultado;
    }
}
