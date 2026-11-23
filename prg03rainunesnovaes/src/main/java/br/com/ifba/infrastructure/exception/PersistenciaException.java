package br.com.ifba.infrastructure.exception;

// lancada pelo dao quando o banco nao consegue fazer a operacao
// (banco fora do ar, login repetido barrado pelo UNIQUE, campo obrigatorio vazio...)
// assim a tela recebe uma excecao do projeto, e nao uma excecao do Hibernate
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String operacao, Throwable causa) {
        super("Nao foi possivel " + operacao + " no banco de dados: " + causa.getMessage(), causa);
    }
}
