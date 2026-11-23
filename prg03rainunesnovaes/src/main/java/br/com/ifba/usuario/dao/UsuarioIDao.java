package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.dao.GenericIDao;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// o CRUD basico vem do GenericIDao, aqui ficam so as buscas proprias do usuario
public interface UsuarioIDao extends GenericIDao<Usuario> {

    Usuario findByLogin(String login);

    List<Usuario> findByNome(String nome);

    boolean existsByLogin(String login);
}
