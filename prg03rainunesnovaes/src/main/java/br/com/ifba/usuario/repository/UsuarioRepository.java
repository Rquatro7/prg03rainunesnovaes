package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

// o Spring Data cria a implementacao sozinho, a partir do nome dos metodos
public interface UsuarioRepository extends Repository<Usuario, Long>, UsuarioIDao {

    // consulta derivada: o Spring monta o "select count" pelo nome
    @Override
    boolean existsByLogin(String login);

    Optional<Usuario> readById(Long id);

    Optional<Usuario> readByLogin(String login);

    List<Usuario> findByNomeContainingIgnoreCaseOrderByNome(String nome);

    // usuarios que recebem ofertas no Telegram, filtrando pelo nome e em ordem alfabetica
    @Query("SELECT u FROM Usuario u WHERE u.chatIdTelegram IS NOT NULL "
            + "AND LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY u.nome")
    List<Usuario> buscarComTelegramPorNome(@Param("nome") String nome);

    @Override
    default Usuario update(Usuario usuario) {
        save(usuario);
        return usuario;
    }

    @Override
    default Usuario findById(Long id) {
        return readById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Override
    default Usuario findByLogin(String login) {
        return readByLogin(login).orElseThrow(() -> new UsuarioNaoEncontradoException(login));
    }

    @Override
    default List<Usuario> findByNome(String nome) {
        return findByNomeContainingIgnoreCaseOrderByNome(nome.trim());
    }
}
