package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.infrastructure.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

// dao generico: o CRUD basico fica escrito uma vez so e serve para qualquer entidade
// o tipo da entidade chega pelo construtor, porque o generics some depois de compilar
public abstract class GenericDao<Entity extends PersistenceEntity> implements GenericIDao<Entity> {

    private final Class<Entity> tipo;
    private final Supplier<EntityManager> fabricaDeEntityManager;

    protected GenericDao(Class<Entity> tipo) {
        this(tipo, JPAUtil::getEntityManager);
    }

    // permite trocar de onde vem o EntityManager (usado nos testes para simular o banco fora do ar)
    protected GenericDao(Class<Entity> tipo, Supplier<EntityManager> fabricaDeEntityManager) {
        this.tipo = tipo;
        this.fabricaDeEntityManager = fabricaDeEntityManager;
    }

    // cada dao especifico diz qual excecao do dominio usar quando o id nao existe
    protected abstract RuntimeException naoEncontrado(Long id);

    @Override
    public void save(Entity entity) {
        EntityManager em = abrir();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            desfazer(em);
            throw new PersistenciaException("salvar " + nomeDoTipo(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity update(Entity entity) {
        EntityManager em = abrir();
        try {
            em.getTransaction().begin();
            Entity atualizada = em.merge(entity);
            em.getTransaction().commit();
            return atualizada;
        } catch (PersistenceException ex) {
            desfazer(em);
            throw new PersistenciaException("atualizar " + nomeDoTipo(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Entity entity) {
        EntityManager em = abrir();
        try {
            Entity encontrada = em.find(tipo, entity.getId());
            if (encontrada == null) {
                throw naoEncontrado(entity.getId());
            }
            em.getTransaction().begin();
            em.remove(encontrada);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            desfazer(em);
            throw new PersistenciaException("excluir " + nomeDoTipo(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public Entity findById(Long id) {
        Entity encontrada = consultar(em -> em.find(tipo, id));
        if (encontrada == null) {
            throw naoEncontrado(id);
        }
        return encontrada;
    }

    @Override
    public List<Entity> findAll() {
        return consultar(em -> em.createQuery("SELECT e FROM " + nomeDoTipo() + " e", tipo).getResultList());
    }

    // usado pelos daos especificos para fazer consultas sem repetir o abrir/fechar
    protected <R> R consultar(Function<EntityManager, R> consulta) {
        EntityManager em = abrir();
        try {
            return consulta.apply(em);
        } catch (PersistenceException ex) {
            throw new PersistenciaException("consultar " + nomeDoTipo(), ex);
        } finally {
            em.close();
        }
    }

    protected Class<Entity> getTipo() {
        return tipo;
    }

    // conectar tambem pode falhar (banco desligado, senha errada), e isso vira a excecao do projeto
    private EntityManager abrir() {
        try {
            return fabricaDeEntityManager.get();
        } catch (RuntimeException ex) {
            throw new PersistenciaException("conectar", ex);
        }
    }

    private void desfazer(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    private String nomeDoTipo() {
        return tipo.getSimpleName();
    }
}
