package br.com.ifba.usuario.interfaces;

// interface que define o contrato de autenticacao
public interface Autenticavel {
    boolean autenticar(String login, String senha);
}