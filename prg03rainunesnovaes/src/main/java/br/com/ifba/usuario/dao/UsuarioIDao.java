package br.com.ifba.usuario.dao;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// contrato que a service usa para acessar os usuarios
// quem implementa no programa e o UsuarioRepository (Spring Data); nos testes e o FakeUsuarioDao
public interface UsuarioIDao {

    void save(Usuario usuario);

    Usuario update(Usuario usuario);

    void delete(Usuario usuario);

    Usuario findById(Long id);

    List<Usuario> findAll();

    Usuario findByLogin(String login);

    List<Usuario> findByNome(String nome);

    boolean existsByLogin(String login);
}
