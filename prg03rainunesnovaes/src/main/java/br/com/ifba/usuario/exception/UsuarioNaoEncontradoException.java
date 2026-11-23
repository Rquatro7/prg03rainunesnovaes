package br.com.ifba.usuario.exception;

// lancada quando uma busca por usuario nao encontra ninguem
public class UsuarioNaoEncontradoException extends RuntimeException {

    // busca feita pelo login
    public UsuarioNaoEncontradoException(String login) {
        super("Nenhum usuario encontrado com o login '" + login + "'.");
    }

    // busca feita pelo id
    public UsuarioNaoEncontradoException(Long id) {
        super("Nenhum usuario encontrado com o id " + id + ".");
    }
}
