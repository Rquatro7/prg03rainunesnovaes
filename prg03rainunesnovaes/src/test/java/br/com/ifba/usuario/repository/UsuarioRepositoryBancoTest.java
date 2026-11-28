package br.com.ifba.usuario.repository;

import br.com.ifba.ProjetoApplication;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

// teste que usa o MySQL de verdade, rodar com: mvn test -Pbanco
@Tag("banco")
public class UsuarioRepositoryBancoTest {

    private static ConfigurableApplicationContext context;
    private static UsuarioRepository repository;

    @BeforeAll
    public static void subirSpring() {
        context = new SpringApplicationBuilder(ProjetoApplication.class).headless(false).run();
        repository = context.getBean(UsuarioRepository.class);
    }

    @AfterAll
    public static void fecharSpring() {
        context.close();
    }

    private Usuario novoUsuario(String nome, String login, String chatTelegram) {
        Usuario usuario = new Usuario(nome, "12345678900", login, "123456");
        usuario.setChatIdTelegram(chatTelegram);
        repository.save(usuario);
        return usuario;
    }

    @Test
    public void crud_deveGravarBuscarAtualizarERemover() {
        Usuario usuario = novoUsuario("Teste Crud", "teste_crud_" + System.nanoTime(), null);
        assertNotNull(usuario.getId());

        usuario.setNome("Teste Crud Editado");
        repository.update(usuario);
        assertEquals("Teste Crud Editado", repository.findById(usuario.getId()).getNome());

        repository.delete(usuario);
        assertThrows(UsuarioNaoEncontradoException.class, () -> repository.findById(usuario.getId()));
    }

    // Task 04: consulta derivada existsByLogin
    @Test
    public void existsByLogin_deveDizerSeOLoginJaExiste() {
        String login = "teste_exists_" + System.nanoTime();
        assertFalse(repository.existsByLogin(login));

        Usuario usuario = novoUsuario("Teste Exists", login, null);
        assertTrue(repository.existsByLogin(login));

        repository.delete(usuario);
    }

    // Task 05: consulta com @Query
    @Test
    public void buscarComTelegramPorNome_deveTrazerSoQuemTemTelegramEmOrdemDeNome() {
        String marca = "Zq" + System.nanoTime();
        Usuario carla = novoUsuario("Carla " + marca, "teste_q1_" + marca, "111");
        Usuario bruno = novoUsuario("Bruno " + marca, "teste_q2_" + marca, "222");
        Usuario semTelegram = novoUsuario("Ana " + marca, "teste_q3_" + marca, null);

        List<Usuario> encontrados = repository.buscarComTelegramPorNome(marca.toLowerCase());

        assertEquals(2, encontrados.size());
        assertEquals(bruno.getId(), encontrados.get(0).getId());
        assertEquals(carla.getId(), encontrados.get(1).getId());

        repository.delete(carla);
        repository.delete(bruno);
        repository.delete(semTelegram);
    }
}
