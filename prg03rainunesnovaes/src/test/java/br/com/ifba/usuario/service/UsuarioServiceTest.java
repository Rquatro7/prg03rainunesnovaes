package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// testes das regras de negocio da service usando o FakeUsuarioDao, nenhum depende do banco
public class UsuarioServiceTest {

    private FakeUsuarioDao dao;
    private UsuarioService service;

    @BeforeEach
    public void preparar() {
        dao = new FakeUsuarioDao();
        service = new UsuarioService(dao);
    }

    // usuario com todos os campos certos, cada teste estraga so o que quer testar
    private Usuario usuarioValido(String nome, String login) {
        Usuario usuario = new Usuario(nome, "123.456.789-00", login, "123456");
        usuario.setDataNascimento("01/01/2000");
        usuario.setTelefone("74999990000");
        usuario.setEmail(login + "@email.com");
        usuario.setGenero("Masculino");
        return usuario;
    }

    // ---------- cadastrar: caminho feliz ----------

    @Test
    public void cadastrar_naoDeveLancarExcecao_quandoDadosValidos() {
        Usuario usuario = usuarioValido("Rai", "rainunes");

        assertDoesNotThrow(() -> service.cadastrar(usuario, "123456"));
        assertEquals(1, dao.getVezesQueSalvou());
        assertNotNull(usuario.getId());
    }

    // ---------- cadastrar: regras que antes estavam na tela ----------

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoCampoVazio() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        usuario.setEmail("   ");

        assertThrows(UsuarioInvalidoException.class, () -> service.cadastrar(usuario, "123456"));
        assertEquals(0, dao.getVezesQueSalvou());
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoCampoNulo() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        usuario.setTelefone(null);

        assertThrows(UsuarioInvalidoException.class, () -> service.cadastrar(usuario, "123456"));
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoConfirmacaoVazia() {
        assertThrows(UsuarioInvalidoException.class,
                () -> service.cadastrar(usuarioValido("Rai", "rainunes"), ""));
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoCpfInvalido() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        usuario.setCpf("123");

        UsuarioInvalidoException ex = assertThrows(UsuarioInvalidoException.class,
                () -> service.cadastrar(usuario, "123456"));
        assertEquals("CPF inválido.", ex.getMessage());
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoSenhasDiferentes() {
        assertThrows(UsuarioInvalidoException.class,
                () -> service.cadastrar(usuarioValido("Rai", "rainunes"), "654321"));
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoSenhaCurta() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        usuario.setSenha("12345");

        assertThrows(UsuarioInvalidoException.class, () -> service.cadastrar(usuario, "12345"));
    }

    @Test
    public void cadastrar_deveAceitarSenhaNoLimiteDeSeisCaracteres() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        usuario.setSenha("abcdef");

        assertDoesNotThrow(() -> service.cadastrar(usuario, "abcdef"));
    }

    @Test
    public void cadastrar_deveLancarUsuarioInvalido_quandoLoginTemPalavraProibida() {
        assertThrows(UsuarioInvalidoException.class,
                () -> service.cadastrar(usuarioValido("Rai", "admin_rai"), "123456"));
    }

    @Test
    public void cadastrar_deveLancarLoginDuplicado_quandoLoginJaExiste() {
        service.cadastrar(usuarioValido("Rai", "rainunes"), "123456");
        Usuario repetido = usuarioValido("Outro", "rainunes");

        assertThrows(LoginDuplicadoException.class, () -> service.cadastrar(repetido, "123456"));
        assertEquals(1, dao.getVezesQueSalvou()); // o repetido nao pode ter sido salvo
    }

    // ---------- atualizar ----------

    @Test
    public void atualizar_deveSalvarNovoNome_quandoDadosValidos() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        service.cadastrar(usuario, "123456");
        usuario.setNome("Rai Nunes");

        service.atualizar(usuario);

        assertEquals("Rai Nunes", service.buscarPorId(usuario.getId()).getNome());
    }

    @Test
    public void atualizar_deveLancarUsuarioInvalido_quandoNomeVazio() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        service.cadastrar(usuario, "123456");
        usuario.setNome("");

        assertThrows(UsuarioInvalidoException.class, () -> service.atualizar(usuario));
    }

    @Test
    public void atualizar_deveLancarUsuarioInvalido_quandoCpfInvalido() {
        Usuario usuario = usuarioValido("Rai", "rainunes");
        service.cadastrar(usuario, "123456");
        usuario.setCpf("abc");

        assertThrows(UsuarioInvalidoException.class, () -> service.atualizar(usuario));
    }

    // ---------- autenticar ----------

    @Test
    public void autenticar_deveDevolverTrue_quandoLoginESenhaCorretos() {
        service.cadastrar(usuarioValido("Rai", "rainunes"), "123456");

        assertTrue(service.autenticar("rainunes", "123456"));
    }

    @Test
    public void autenticar_deveDevolverFalse_quandoSenhaErrada() {
        service.cadastrar(usuarioValido("Rai", "rainunes"), "123456");

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
        Usuario usuario = usuarioValido("Rai", "rainunes");
        service.cadastrar(usuario, "123456");

        assertDoesNotThrow(() -> service.remover(usuario.getId()));
        assertTrue(service.buscarPorNome("").isEmpty());
    }

    @Test
    public void buscarPorNome_deveTrazerTodos_quandoTextoVazioOuNulo() {
        service.cadastrar(usuarioValido("Rai", "rainunes"), "123456");
        service.cadastrar(usuarioValido("Ana", "ana"), "123456");

        assertEquals(2, service.buscarPorNome("").size());
        assertEquals(2, service.buscarPorNome(null).size());
    }

    @Test
    public void buscarPorNome_deveFiltrarPeloNome() {
        service.cadastrar(usuarioValido("Rai Nunes", "rainunes"), "123456");
        service.cadastrar(usuarioValido("Ana", "ana"), "123456");

        assertEquals(1, service.buscarPorNome("nunes").size());
    }
}
