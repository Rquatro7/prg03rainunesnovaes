package br.com.ifba.usuario.validar;

/**
 * Centraliza as regras de validacao do cadastro de usuario,
 * sem nenhuma dependencia de componentes de tela.
 */
public class ValidadorUsuario {

    private static final int TAMANHO_MINIMO_SENHA = 6;
    private static final int TAMANHO_CPF = 11;
    private static final String[] PALAVRAS_PROIBIDAS = {"admin", "teste", "root", "senha123"};

    /**
     * Verifica se nenhum dos campos informados esta vazio ou nulo.
     */
    public static boolean camposPreenchidos(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se a senha e a confirmacao sao iguais.
     */
    public static boolean senhasCoincidem(String senha, String confirmarSenha) {
        if (senha == null || confirmarSenha == null) {
            return false;
        }
        return senha.equals(confirmarSenha);
    }

    /**
     * Verifica se a senha tem o tamanho minimo exigido.
     */
    public static boolean senhaForte(String senha) {
        if (senha == null) {
            return false;
        }
        return senha.length() >= TAMANHO_MINIMO_SENHA;
    }

    /**
     * Verifica se o CPF tem 11 digitos numericos (ignora pontuacao).
     */
    public static boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        String apenasNumeros = cpf.replaceAll("[^0-9]", "");
        return apenasNumeros.length() == TAMANHO_CPF;
    }

    /**
     * Verifica se o texto contem alguma palavra proibida.
     */
    public static boolean contemPalavraProibida(String texto) {
        if (texto == null) {
            return false;
        }
        for (String palavra : PALAVRAS_PROIBIDAS) {
            if (texto.toLowerCase().contains(palavra)) {
                return true;
            }
        }
        return false;
    }
}