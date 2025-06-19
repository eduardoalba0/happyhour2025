package br.edu.ifpr.bsi.happyhour.helpers;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class HibernateHelperTest {

    @Test
    public void conectar(){
        Session sessao = HibernateHelper.getFabricaDeSessoes().openSession();
        sessao.close();
    }

}
