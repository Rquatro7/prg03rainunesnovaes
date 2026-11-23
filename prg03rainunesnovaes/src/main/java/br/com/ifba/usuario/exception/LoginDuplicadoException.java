package br.com.ifba.usuario.exception;

// lancada quando alguem tenta cadastrar um login que ja existe
public class LoginDuplicadoException extends RuntimeException {

    public LoginDuplicadoException(String login) {
        super("Ja existe um usuario cadastrado com o login '" + login + "'.");
    }
}
