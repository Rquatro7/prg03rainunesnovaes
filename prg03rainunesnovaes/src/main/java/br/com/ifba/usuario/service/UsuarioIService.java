package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

// regras de negocio do usuario
public interface UsuarioIService {

    void cadastrar(Usuario usuario, String confirmarSenha);

    Usuario atualizar(Usuario usuario);

    void remover(Long id);

    Usuario buscarPorId(Long id);

    List<Usuario> buscarPorNome(String nome);

    boolean autenticar(String login, String senha);
}
