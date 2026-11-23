package br.com.ifba.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// centraliza a criacao da fabrica de EntityManager
// a fabrica e cara de criar, entao ela e criada uma unica vez e reaproveitada
public class JPAUtil {

    private static final String UNIDADE_PERSISTENCIA = "prg03PU";
    private static EntityManagerFactory fabrica;

    // construtor privado: ninguem precisa instanciar essa classe
    private JPAUtil() {
    }

    // devolve um EntityManager novo, criando a fabrica na primeira chamada
    public static synchronized EntityManager getEntityManager() {
        if (fabrica == null || !fabrica.isOpen()) {
            // as migrations rodam antes, porque com validate o Hibernate so confere as tabelas
            MigracaoBanco.migrar();
            fabrica = Persistence.createEntityManagerFactory(UNIDADE_PERSISTENCIA);
        }
        return fabrica.createEntityManager();
    }

    // fecha a fabrica, usado quando a aplicacao for encerrada
    public static synchronized void fechar() {
        if (fabrica != null && fabrica.isOpen()) {
            fabrica.close();
        }
        fabrica = null;
    }
}
