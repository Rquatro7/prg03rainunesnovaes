package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void autenticar_deveRetornarTrue_quandoCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        assertTrue(usuario.autenticar("rainunes", "123456"));
    }

    @Test
    public void autenticar_deveRetornarFalse_quandoSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        assertFalse(usuario.autenticar("rainunes", "senhaerrada"));
    }
}
