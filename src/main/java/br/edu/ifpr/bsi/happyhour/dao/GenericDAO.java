package br.edu.ifpr.bsi.happyhour.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.edu.ifpr.bsi.happyhour.helpers.HibernateHelper;

public class GenericDAO<Entidade> {
    private final Class<Entidade> classe;

    public GenericDAO() {
        this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void merge(Entidade entidade) {
        Transaction transacao = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            transacao = session.beginTransaction();
            // O metodo Merge serve tanto para inserir, quanto para atualizar
            // Se o dado ja estiver no banco, ele vai alterar o que mudou
            // Se o dado não estiver no banco, ele vai inseri-lo
            session.merge(entidade);
            transacao.commit();
        } catch (RuntimeException erro) {
            if (transacao != null) {
                transacao.rollback();
            }
            System.out.println("Ocorreu um erro ao inserir/atualizar os dados.");
            throw erro;
        } finally {
            session.close();
        }
    }

    public void deletar(Entidade entidade) {
        Transaction transacao = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            transacao = session.beginTransaction();
            session.remove(entidade);
            transacao.commit();
        } catch (RuntimeException erro) {
            if (transacao != null) {
                transacao.rollback();
            }
            erro.printStackTrace();
            System.out.println("Ocorreu um erro ao remover os dados.");
            throw erro;
        } finally {
            session.close();
        }
    }

    public List<Entidade> listar() {
        List<Entidade> resultado = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Entidade> criteria = builder.createQuery(classe);
            Root<Entidade> root = criteria.from(classe);
            criteria.select(root);
            resultado = session.createQuery(criteria).getResultList();
        } catch (RuntimeException erro) {
            // Não precisa fazer rollback
            // Porque não há mudança no banco de dados
            System.out.println("Ocorreu um erro ao listar os dados.");
            throw erro;
        } finally {
            session.close();
        }
        return resultado;
    }

    public Entidade listar(Long codigo) {
        Entidade resultado = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Entidade> criteria = builder.createQuery(classe);
            Root<Entidade> root = criteria.from(classe);
            // Os critérios de busca são definidos aqui
            // O codigo é o nome do atributo que está nos parâmetros deste metodo
            // O where quer dizer que o comando SQL que estamos emulando é a cláusula "WHERE"
            criteria.select(root).where(builder.equal(root.get("codigo"), codigo));
            resultado = session.createQuery(criteria).getSingleResult();
        } catch (RuntimeException erro) {
            // Não precisa fazer rollback
            // Porque não há mudança no banco de dados
            System.out.println("Ocorreu um erro ao buscar a entidade.");
            throw erro;
        } finally {
            session.close();
        }
        return resultado;
    }

    public List<Entidade> listar(String campoOrdenacao) {
        List<Entidade> resultado = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Entidade> criteria = builder.createQuery(classe);
            Root<Entidade> root = criteria.from(classe);
            criteria.select(root);
            criteria.orderBy(builder.asc(root.get(campoOrdenacao)));
            resultado = session.createQuery(criteria).getResultList();
        } catch (RuntimeException erro) {
            System.out.println("Ocorreu um erro ao listar os dados.");
            throw erro;
        } finally {
            session.close();
        }
        return resultado;
    }

    public List<Entidade> listar(String campo, String campoOrdenacao, Object valor) {
        List<Entidade> resultado = null;
        Session session = HibernateHelper.getFabricaDeSessoes().openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Entidade> criteria = builder.createQuery(classe);
            Root<Entidade> root = criteria.from(classe);
            criteria.select(root).where(builder.equal(root.get(campo), valor))
                    .orderBy(builder.asc(root.get(campoOrdenacao)));
            resultado = session.createQuery(criteria).getResultList();
        } catch (RuntimeException erro) {
            System.out.println("Ocorreu um erro ao listar os dados.");
            throw erro;
        } finally {
            session.close();
        }
        return resultado;
    }
}