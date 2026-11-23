package br.com.ifba;

import br.com.ifba.infrastructure.util.MigracaoBanco;
import br.com.ifba.login.view.TelaLogin;
import br.com.ifba.usuario.controller.UsuarioController;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.service.UsuarioService;
import br.com.ifba.usuario.view.TelaCadastroUsuario;
import br.com.ifba.usuario.view.TelaEdicaoUsuario;
import br.com.ifba.usuario.view.TelaListagemUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

// ponto de entrada da aplicacao
// e o unico lugar que da new nas camadas: monta dao -> service -> controller -> telas
// e entrega cada dependencia pronta pelo construtor
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        usarVisualNimbus();

        // aplica as migrations do Flyway antes de abrir a primeira tela
        try {
            MigracaoBanco.migrar();
        } catch (RuntimeException ex) {
            logger.log(Level.SEVERE, "Erro ao aplicar as migrations", ex);
            JOptionPane.showMessageDialog(null, "Nao foi possivel atualizar o banco de dados.\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

        java.awt.EventQueue.invokeLater(() -> {
            UsuarioIController controller = new UsuarioController(new UsuarioService(new UsuarioDao()));
            TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(controller);
            TelaEdicaoUsuario telaEdicao = new TelaEdicaoUsuario(controller);
            TelaListagemUsuario telaListagem = new TelaListagemUsuario(controller, telaCadastro, telaEdicao);
            new TelaLogin(controller, telaListagem, telaCadastro).setVisible(true);
        });
    }

    private static void usarVisualNimbus() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
