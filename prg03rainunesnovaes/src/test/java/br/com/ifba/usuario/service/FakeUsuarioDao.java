package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;

// dublê do dao: guarda os usuarios numa List em memoria
// assim os testes da service rodam sem banco nenhum
public class FakeUsuarioDao implements UsuarioIDao {

    private final List<Usuario> usuarios = new ArrayList<>();
    private long proximoId = 1;
    private int vezesQueSalvou = 0;

    @Override
    public void save(Usuario usuario) {
        usuario.setId(proximoId++);
        usuarios.add(usuario);
        vezesQueSalvou++;
    }

    @Override
    public Usuario update(Usuario usuario) {
        Usuario antigo = findById(usuario.getId());
        usuarios.set(usuarios.indexOf(antigo), usuario);
        return usuario;
    }

    @Override
    public void delete(Usuario usuario) {
        usuarios.remove(findById(usuario.getId()));
    }

    @Override
    public Usuario findById(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(id);
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> findByNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().toLowerCase().contains(nome.trim().toLowerCase())) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }

    @Override
    public Usuario findByLogin(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public int getVezesQueSalvou() {
        return vezesQueSalvou;
    }
}
