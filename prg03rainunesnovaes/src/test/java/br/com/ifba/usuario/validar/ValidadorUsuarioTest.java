package br.com.ifba.usuario.validar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorUsuarioTest {

    // ---------- camposPreenchidos ----------

    @Test
    public void camposPreenchidos_deveRetornarTrue_quandoTodosPreenchidos() {
        assertTrue(ValidadorUsuario.camposPreenchidos("Rai", "123", "login"));
    }

    @Test
    public void camposPreenchidos_deveRetornarFalse_quandoCampoVazio() {
        assertFalse(ValidadorUsuario.camposPreenchidos("Rai", "", "login"));
    }

    @Test
    public void camposPreenchidos_deveRetornarFalse_quandoCampoNull() {
        assertFalse(ValidadorUsuario.camposPreenchidos("Rai", null, "login"));
    }

    // ---------- senhasCoincidem ----------

    @Test
    public void senhasCoincidem_deveRetornarTrue_quandoIguais() {
        assertTrue(ValidadorUsuario.senhasCoincidem("123456", "123456"));
    }

    @Test
    public void senhasCoincidem_deveRetornarFalse_quandoDiferentes() {
        assertFalse(ValidadorUsuario.senhasCoincidem("123456", "654321"));
    }

    // ---------- senhaForte ----------

    @Test
    public void senhaForte_deveRetornarTrue_quandoTamanhoMinimo() {
        assertTrue(ValidadorUsuario.senhaForte("123456"));
    }

    @Test
    public void senhaForte_deveRetornarFalse_quandoMenorQueMinimo() {
        assertFalse(ValidadorUsuario.senhaForte("123"));
    }

    @Test
    public void senhaForte_deveRetornarFalse_quandoNull() {
        assertFalse(ValidadorUsuario.senhaForte(null));
    }

    // ---------- cpfValido ----------

    @Test
    public void cpfValido_deveRetornarTrue_quandoOnzeDigitos() {
        assertTrue(ValidadorUsuario.cpfValido("12345678900"));
    }

    @Test
    public void cpfValido_deveRetornarTrue_quandoComPontuacao() {
        assertTrue(ValidadorUsuario.cpfValido("123.456.789-00"));
    }

    @Test
    public void cpfValido_deveRetornarFalse_quandoVazio() {
        assertFalse(ValidadorUsuario.cpfValido(""));
    }

    @Test
    public void cpfValido_deveRetornarFalse_quandoComLetras() {
        assertFalse(ValidadorUsuario.cpfValido("abc.def.ghi-00"));
    }

    // ---------- contemPalavraProibida ----------

    @Test
    public void contemPalavraProibida_deveRetornarTrue_quandoContemPalavra() {
        assertTrue(ValidadorUsuario.contemPalavraProibida("admin"));
    }

    @Test
    public void contemPalavraProibida_deveRetornarFalse_quandoNaoContem() {
        assertFalse(ValidadorUsuario.contemPalavraProibida("rainunes"));
    }
}