package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// testes da service usando um dao falso, nao precisa do banco ligado
public class UsuarioServiceTest {

    private UsuarioDaoFalso dao;
    private UsuarioService service;

    @BeforeEach
    public void preparar() {
        dao = new UsuarioDaoFalso();
        service = new UsuarioService(dao);
    }

    // ---------- cadastrar ----------

    @Test
    public void cadastrar_naoDeveLancarExcecao_quandoLoginENovo() {
        Usuario usuario = new Usuario("Rai", "12345678900", "rainunes", "123456");

        assertDoesNotThrow(() -> service.cadastrar(usuario));
        assertEquals(1, dao.getVezesQueSalvou());
    }

    @Test
    public void cadastrar_deveLancarLoginDuplicado_quandoLoginJaExiste() {
        service.cadastrar(new Usuario("Rai", "12345678900", "rainunes", "123456"));
        Usuario repetido = new Usuario("Outro", "98765432100", "rainunes", "654321");

        assertThrows(LoginDuplicadoException.class, () -> service.cadastrar(repetido));
        assertEquals(1, dao.getVezesQueSalvou()); // o repetido nao pode ter sido salvo
    }

    // ---------- autenticar ----------

    @Test
    public void autenticar_deveDevolverTrue_quandoLoginESenhaCorretos() {
        service.cadastrar(new Usuario("Rai", "12345678900", "rainunes", "123456"));

        assertTrue(service.autenticar("rainunes", "123456"));
    }

    @Test
    public void autenticar_deveDevolverFalse_quandoSenhaErrada() {
        service.cadastrar(new Usuario("Rai", "12345678900", "rainunes", "123456"));

        assertFalse(service.autenticar("rainunes", "errada"));
    }

    @Test
    public void autenticar_deveLancarUsuarioNaoEncontrado_quandoLoginNaoExiste() {
        assertThrows(UsuarioNaoEncontradoException.class, () -> service.autenticar("ninguem", "123456"));
    }

    // ---------- buscar e remover ----------

    @Test
    public void buscarPorId_deveLancarUsuarioNaoEncontrado_quandoIdNaoExiste() {
        assertThrows(UsuarioNaoEncontradoException.class, () -> service.buscarPorId(99L));
    }

    @Test
    public void remover_deveLancarUsuarioNaoEncontrado_quandoIdNaoExiste() {
        assertThrows(UsuarioNaoEncontradoException.class, () -> service.remover(99L));
    }

    @Test
    public void remover_deveTirarUsuarioDaLista_quandoIdExiste() {
        Usuario usuario = new Usuario("Rai", "12345678900", "rainunes", "123456");
        service.cadastrar(usuario);

        assertDoesNotThrow(() -> service.remover(usuario.getId()));
        assertTrue(service.buscarPorNome("").isEmpty());
    }

    @Test
    public void buscarPorNome_deveTrazerTodos_quandoTextoVazioOuNulo() {
        service.cadastrar(new Usuario("Rai", "12345678900", "rainunes", "123456"));
        service.cadastrar(new Usuario("Ana", "98765432100", "ana", "654321"));

        assertEquals(2, service.buscarPorNome("").size());
        assertEquals(2, service.buscarPorNome(null).size());
    }

    @Test
    public void buscarPorNome_deveFiltrarPeloNome() {
        service.cadastrar(new Usuario("Rai Nunes", "12345678900", "rainunes", "123456"));
        service.cadastrar(new Usuario("Ana", "98765432100", "ana", "654321"));

        assertEquals(1, service.buscarPorNome("nunes").size());
    }
}
