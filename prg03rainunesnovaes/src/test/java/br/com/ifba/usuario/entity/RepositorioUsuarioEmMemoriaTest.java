package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioUsuarioEmMemoriaTest {

    @Test
    public void cadastrar_deveFazerUsuarioAparecerEmListarTodos() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario = new Usuario("Rai", "12345678900", "rainunes", "123456");

        repositorio.cadastrar(usuario);

        assertTrue(repositorio.listarTodos().contains(usuario));
    }

    @Test
    public void buscarPorLogin_deveDevolverUsuarioCerto_quandoDoisCadastrados() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario1 = new Usuario("Rai", "12345678900", "rainunes", "123456");
        Usuario usuario2 = new Usuario("Ana", "98765432100", "ana", "654321");

        repositorio.cadastrar(usuario1);
        repositorio.cadastrar(usuario2);

        assertEquals(usuario2, repositorio.buscarPorLogin("ana"));
    }

    @Test
    public void buscarPorLogin_deveDevolverNull_quandoLoginNaoExiste() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();

        assertNull(repositorio.buscarPorLogin("naoexiste"));
    }

    @Test
    public void cadastrar_deveLancarExcecao_quandoLoginJaExiste() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario1 = new Usuario("Rai", "12345678900", "rainunes", "123456");
        Usuario usuario2 = new Usuario("Outro Rai", "11122233344", "rainunes", "senha2");

        repositorio.cadastrar(usuario1);

        assertThrows(IllegalArgumentException.class, () -> repositorio.cadastrar(usuario2));
    }

    // ---------- prova do equals (Task 02) ----------

    @Test
    public void listarTodos_deveConsiderarUsuariosComMesmoLoginComoIguais() {
        RepositorioUsuarioEmMemoria repositorio = new RepositorioUsuarioEmMemoria();
        Usuario usuario1 = new Usuario("Rai", "12345678900", "rainunes", "123456");
        Usuario usuario2 = new Usuario("Rai Diferente", "99988877766", "rainunes", "outrasenha");

        repositorio.cadastrar(usuario1);

        // usuario2 e um objeto diferente, mas com o mesmo login de usuario1
        // graças ao equals, a lista reconhece os dois como o mesmo usuario
        assertTrue(repositorio.listarTodos().contains(usuario2));
    }
}