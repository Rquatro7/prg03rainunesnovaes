package br.com.ifba.usuario.entity;

import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// repositorio que guarda os usuarios cadastrados em memoria,
// enquanto a aplicacao estiver rodando
public class RepositorioUsuarioEmMemoria {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Usuario> usuariosPorLogin = new HashMap<>(); // indice por login, para busca rapida

    // adiciona um usuario na lista e no indice, impedindo login duplicado
    public void cadastrar(Usuario usuario) {
        if (usuariosPorLogin.containsKey(usuario.getLogin())) {
            throw new IllegalArgumentException("Ja existe um usuario cadastrado com esse login.");
        }
        usuarios.add(usuario);
        usuariosPorLogin.put(usuario.getLogin(), usuario);
    }

    // devolve todos os usuarios cadastrados
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    // busca percorrendo a lista com um for (Task 03, primeira forma)
    public Usuario buscarPorLoginComFor(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        throw new UsuarioNaoEncontradoException(login);
    }

    // busca usando o map (Task 03, segunda forma) - e o metodo oficial do repositorio
    // antes devolvia null quando nao achava, agora lanca a excecao no lugar onde o problema aparece
    public Usuario buscarPorLogin(String login) {
        Usuario usuario = usuariosPorLogin.get(login);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(login);
        }
        return usuario;
    }
}
