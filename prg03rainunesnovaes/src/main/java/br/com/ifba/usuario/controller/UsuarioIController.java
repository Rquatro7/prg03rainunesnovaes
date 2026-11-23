package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// porta de entrada das telas: a tela so conversa com o controller
public interface UsuarioIController {

    void salvar(Usuario usuario, String confirmarSenha);

    Usuario atualizar(Usuario usuario);

    void excluir(Long id);

    Usuario buscarPorId(Long id);

    List<Usuario> listar(String filtroNome);

    boolean autenticar(String login, String senha);
}
