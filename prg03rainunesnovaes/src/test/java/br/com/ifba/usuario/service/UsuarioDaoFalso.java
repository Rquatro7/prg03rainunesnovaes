package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// dao em memoria usado so nos testes, para testar a service sem precisar do MySQL
public class UsuarioDaoFalso implements UsuarioIDao {

    private final Map<Long, Usuario> usuarios = new LinkedHashMap<>();
    private long proximoId = 1;
    private int vezesQueSalvou = 0;

    @Override
    public void save(Usuario usuario) {
        usuario.setId(proximoId++);
        usuarios.put(usuario.getId(), usuario);
        vezesQueSalvou++;
    }

    @Override
    public Usuario update(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public void delete(Usuario usuario) {
        if (usuarios.remove(usuario.getId()) == null) {
            throw new UsuarioNaoEncontradoException(usuario.getId());
        }
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public List<Usuario> findByNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().toLowerCase().contains(nome.trim().toLowerCase())) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(id);
        }
        return usuario;
    }

    @Override
    public Usuario findByLogin(String login) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        for (Usuario usuario : usuarios.values()) {
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
