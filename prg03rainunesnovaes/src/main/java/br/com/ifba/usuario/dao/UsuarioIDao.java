package br.com.ifba.usuario.dao;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// contrato de acesso aos dados de usuario
// a service depende so dessa interface, assim da para testar sem banco
public interface UsuarioIDao {

    void salvar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    void remover(Long id);

    List<Usuario> listarTodos();

    List<Usuario> buscarPorNome(String nome);

    Usuario buscarPorId(Long id);

    Usuario buscarPorLogin(String login);

    boolean existeLogin(String login);
}
