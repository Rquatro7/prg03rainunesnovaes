package br.com.ifba.usuario.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// repositorio que guarda os usuarios cadastrados em memoria,
// enquanto a aplicacao estiver rodando
public class RepositorioUsuarioEmMemoria {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final Map<String, Usuario> porLogin = new HashMap<>(); // indice por login, para busca rapida

    // adiciona um usuario na lista e no indice, impedindo login duplicado
    public void cadastrar(Usuario usuario) {
        if (porLogin.containsKey(usuario.getLogin())) {
            throw new IllegalArgumentException("Ja existe um usuario cadastrado com esse login.");
        }
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario);
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
        return null;
    }

    // busca usando o map (Task 03, segunda forma) - e o metodo oficial do repositorio
    public Usuario buscarPorLogin(String login) {
        return porLogin.get(login);
    }
}