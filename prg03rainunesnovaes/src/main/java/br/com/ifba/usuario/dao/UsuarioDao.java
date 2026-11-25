package br.com.ifba.usuario.dao;

import org.springframework.stereotype.Repository;
import br.com.ifba.infrastructure.dao.GenericDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.List;

// dao do usuario: herda o CRUD do GenericDao e implementa as buscas com JPQL
@Repository
public class UsuarioDao extends GenericDao<Usuario> implements UsuarioIDao {

    public UsuarioDao() {
        super(Usuario.class);
    }

    @Override
    protected RuntimeException naoEncontrado(Long id) {
        return new UsuarioNaoEncontradoException(id);
    }

    @Override
    public Usuario findByLogin(String login) {
        List<Usuario> encontrados = consultar(em -> em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getResultList());
        if (encontrados.isEmpty()) {
            throw new UsuarioNaoEncontradoException(login);
        }
        return encontrados.get(0);
    }

    // busca por parte do nome, ignorando maiusculas e minusculas
    @Override
    public List<Usuario> findByNome(String nome) {
        return consultar(em -> em.createQuery(
                "SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE :nome ORDER BY u.nome", Usuario.class)
                .setParameter("nome", "%" + nome.trim().toLowerCase() + "%")
                .getResultList());
    }

    @Override
    public boolean existsByLogin(String login) {
        Long total = consultar(em -> em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login", Long.class)
                .setParameter("login", login)
                .getSingleResult());
        return total > 0;
    }
}
