package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import java.util.List;

// acesso ao banco para a entidade Usuario usando JPA
// toda escrita fica dentro de uma transacao, e o EntityManager sempre e fechado no finally
public class UsuarioDao implements UsuarioIDao {

    @Override
    public void salvar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            desfazer(em);
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario atualizado = em.merge(usuario);
            em.getTransaction().commit();
            return atualizado;
        } catch (RuntimeException ex) {
            desfazer(em);
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException(id);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            desfazer(em);
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // busca com JPQL usando where, ignorando maiusculas e minusculas
    @Override
    public List<Usuario> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE :nome ORDER BY u.nome", Usuario.class)
                    .setParameter("nome", "%" + nome.trim().toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException(id);
            }
            return usuario;
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Usuario> encontrados = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getResultList();
            if (encontrados.isEmpty()) {
                throw new UsuarioNaoEncontradoException(login);
            }
            return encontrados.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existeLogin(String login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long total = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return total > 0;
        } finally {
            em.close();
        }
    }

    // desfaz a transacao se ela ainda estiver aberta
    private void desfazer(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
