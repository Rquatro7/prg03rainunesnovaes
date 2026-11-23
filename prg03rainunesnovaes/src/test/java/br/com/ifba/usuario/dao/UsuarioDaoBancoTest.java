package br.com.ifba.usuario.dao;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.infrastructure.util.JPAUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// teste que usa o MySQL de verdade (persistence.xml real)
// fica fora do mvn test normal, rodar com: mvn test -Pbanco
@Tag("banco")
public class UsuarioDaoBancoTest {

    private final UsuarioDao dao = new UsuarioDao();

    @AfterAll
    public static void fecharFabrica() {
        JPAUtil.fechar();
    }

    private String loginUnico() {
        return "teste_dao_" + System.nanoTime();
    }

    @Test
    public void crudCompleto_deveGravarBuscarAtualizarERemover() {
        String login = loginUnico();
        Usuario usuario = new Usuario("Usuario Banco", "12345678900", login, "123456");

        // cadastrar (persist)
        dao.save(usuario);
        assertNotNull(usuario.getId());
        assertTrue(dao.existsByLogin(login));

        // buscar pelo login, pelo nome (JPQL) e todos
        assertEquals(usuario.getId(), dao.findByLogin(login).getId());
        assertFalse(dao.findByNome("usuario banco").isEmpty());
        assertTrue(dao.findAll().contains(usuario));

        // editar (merge)
        usuario.setNome("Usuario Banco Editado");
        dao.update(usuario);
        assertEquals("Usuario Banco Editado", dao.findById(usuario.getId()).getNome());

        // excluir (remove)
        dao.delete(usuario);
        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.findById(usuario.getId()));
        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.delete(usuario));
    }

    @Test
    public void save_deveLancarPersistenciaException_quandoLoginRepetido() {
        String login = loginUnico();
        Usuario primeiro = new Usuario("Primeiro", "12345678900", login, "123456");
        dao.save(primeiro);

        // o UNIQUE da V3 barra o segundo no banco
        Usuario repetido = new Usuario("Repetido", "98765432100", login, "654321");
        assertThrows(PersistenciaException.class, () -> dao.save(repetido));

        dao.delete(primeiro);
    }

    @Test
    public void save_deveLancarPersistenciaException_quandoLoginNulo() {
        Usuario semLogin = new Usuario("Sem Login", "12345678900", null, "123456");

        assertThrows(PersistenciaException.class, () -> dao.save(semLogin));
    }

    @Test
    public void findByLogin_deveLancarUsuarioNaoEncontrado_quandoLoginNaoExiste() {
        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.findByLogin(loginUnico()));
    }
}
