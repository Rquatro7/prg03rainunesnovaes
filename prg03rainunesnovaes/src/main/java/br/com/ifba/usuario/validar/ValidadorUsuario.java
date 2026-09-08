/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.validar;

/**
 *
 * @author rainu
 */
public class ValidadorUsuario {
    /**
 * Verifica se o texto informado contem alguma palavra proibida.
 * @param texto o texto a ser verificado (ex: login digitado)
 * @return true se contiver alguma palavra proibida, false caso contrario
 */
public static boolean contemPalavraProibida(String texto) {
    // array local com as palavras nao permitidas
    String[] palavrasProibidas = {"admin", "teste", "root", "senha123"};

    // percorre o array verificando se o texto contem alguma delas
    for (String palavra : palavrasProibidas) {
        if (texto.toLowerCase().contains(palavra)) {
            return true;
        }
    }
    return false;
}
}
