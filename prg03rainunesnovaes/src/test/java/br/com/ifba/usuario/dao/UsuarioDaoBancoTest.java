package br.com.ifba.usuario.dao;

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

    @Test
    public void crudCompleto_deveGravarBuscarAtualizarERemover() {
        String login = "teste_dao_" + System.currentTimeMillis();
        Usuario usuario = new Usuario("Usuario Banco", "12345678900", login, "123456");

        // cadastrar (persist)
        dao.salvar(usuario);
        assertNotNull(usuario.getId());
        assertTrue(dao.existeLogin(login));

        // buscar pelo login e pelo nome (JPQL)
        assertEquals(usuario.getId(), dao.buscarPorLogin(login).getId());
        assertFalse(dao.buscarPorNome("usuario banco").isEmpty());

        // editar (merge)
        usuario.setNome("Usuario Banco Editado");
        dao.atualizar(usuario);
        assertEquals("Usuario Banco Editado", dao.buscarPorId(usuario.getId()).getNome());

        // excluir (remove)
        Long id = usuario.getId();
        dao.remover(id);
        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.buscarPorId(id));
        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.remover(id));
    }
}
