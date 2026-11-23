package br.com.ifba.usuario.exception;

// lancada pela service quando os dados do usuario quebram alguma regra de negocio
// (campo vazio, cpf invalido, senha fraca...). A mensagem ja vem pronta para mostrar na tela
public class UsuarioInvalidoException extends RuntimeException {

    public UsuarioInvalidoException(String motivo) {
        super(motivo);
    }
}
