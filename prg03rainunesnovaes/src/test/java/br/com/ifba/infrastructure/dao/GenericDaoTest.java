package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// testa as excecoes do GenericDao sem precisar do MySQL:
// o EntityManager aqui e um "dublê" que falha de proposito
public class GenericDaoTest {

    // dao de teste que usa o EntityManager falso no lugar do JPAUtil
    private static class DaoDeTeste extends GenericDao<Usuario> {
        DaoDeTeste(java.util.function.Supplier<EntityManager> fabrica) {
            super(Usuario.class, fabrica);
        }

        @Override
        protected RuntimeException naoEncontrado(Long id) {
            return new UsuarioNaoEncontradoException(id);
        }
    }

    // guarda o que aconteceu com a transacao e com o EntityManager
    private static class Registro {
        boolean transacaoAtiva = false;
        boolean fezRollback = false;
        boolean fechou = false;
    }

    // monta um EntityManager falso: persist e merge falham, find nao acha nada
    private EntityManager entityManagerQueFalha(Registro registro) {
        EntityTransaction transacao = (EntityTransaction) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityTransaction.class},
                (proxy, metodo, args) -> switch (metodo.getName()) {
                    case "begin" -> { registro.transacaoAtiva = true; yield null; }
                    case "rollback" -> { registro.fezRollback = true; registro.transacaoAtiva = false; yield null; }
                    case "isActive" -> registro.transacaoAtiva;
                    default -> null;
                });
        return (EntityManager) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{EntityManager.class},
                (proxy, metodo, args) -> switch (metodo.getName()) {
                    case "getTransaction" -> transacao;
                    case "persist", "merge" -> throw new PersistenceException("Duplicate entry 'rainunes' for key 'uk_usuario_login'");
                    case "find" -> null;
                    case "close" -> { registro.fechou = true; yield null; }
                    default -> null;
                });
    }

    @Test
    public void save_deveLancarPersistenciaException_quandoBancoRecusa() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));

        assertThrows(PersistenciaException.class,
                () -> dao.save(new Usuario("Rai", "12345678900", "rainunes", "123456")));
    }

    @Test
    public void save_deveFazerRollbackEFecharEntityManager_quandoFalha() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));

        assertThrows(PersistenciaException.class,
                () -> dao.save(new Usuario("Rai", "12345678900", "rainunes", "123456")));

        assertTrue(registro.fezRollback);
        assertTrue(registro.fechou);
    }

    @Test
    public void update_deveLancarPersistenciaException_quandoBancoRecusa() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));

        assertThrows(PersistenciaException.class,
                () -> dao.update(new Usuario("Rai", "12345678900", "rainunes", "123456")));
        assertTrue(registro.fezRollback);
    }

    @Test
    public void qualquerOperacao_deveLancarPersistenciaException_quandoNaoConsegueConectar() {
        DaoDeTeste dao = new DaoDeTeste(() -> {
            throw new PersistenceException("Communications link failure");
        });

        assertThrows(PersistenciaException.class, dao::findAll);
        assertThrows(PersistenciaException.class,
                () -> dao.save(new Usuario("Rai", "12345678900", "rainunes", "123456")));
    }

    @Test
    public void findById_deveLancarExcecaoDoDominio_quandoIdNaoExiste() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));

        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.findById(42L));
        assertTrue(registro.fechou);
    }

    @Test
    public void delete_deveLancarExcecaoDoDominio_semAbrirTransacao_quandoIdNaoExiste() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));
        Usuario inexistente = new Usuario("Rai", "12345678900", "rainunes", "123456");
        inexistente.setId(42L);

        assertThrows(UsuarioNaoEncontradoException.class, () -> dao.delete(inexistente));
        assertFalse(registro.transacaoAtiva);
        assertTrue(registro.fechou);
    }

    @Test
    public void mensagemDaExcecao_deveDizerQualOperacaoFalhou() {
        Registro registro = new Registro();
        DaoDeTeste dao = new DaoDeTeste(() -> entityManagerQueFalha(registro));

        PersistenciaException ex = assertThrows(PersistenciaException.class,
                () -> dao.save(new Usuario("Rai", "12345678900", "rainunes", "123456")));

        assertTrue(ex.getMessage().contains("salvar Usuario"));
    }
}
