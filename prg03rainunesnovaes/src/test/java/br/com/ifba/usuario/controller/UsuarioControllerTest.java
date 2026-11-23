package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
import br.com.ifba.usuario.service.FakeUsuarioDao;
import br.com.ifba.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// confere que o controller so encaminha para a service (sem banco)
public class UsuarioControllerTest {

    private final UsuarioIController controller = new UsuarioController(new UsuarioService(new FakeUsuarioDao()));

    @Test
    public void salvarEListar_devemPassarPelaService() {
        Usuario usuario = new Usuario("Rai", "12345678900", "rainunes", "123456");
        usuario.setDataNascimento("01/01/2000");
        usuario.setTelefone("74999990000");
        usuario.setEmail("rai@email.com");

        controller.salvar(usuario, "123456");

        assertEquals(1, controller.listar("").size());
        assertTrue(controller.autenticar("rainunes", "123456"));
    }

    @Test
    public void salvar_deveRepassarExcecaoDaRegraDeNegocio() {
        Usuario incompleto = new Usuario("Rai", "12345678900", "rainunes", "123456");

        assertThrows(UsuarioInvalidoException.class, () -> controller.salvar(incompleto, "123456"));
    }
}
