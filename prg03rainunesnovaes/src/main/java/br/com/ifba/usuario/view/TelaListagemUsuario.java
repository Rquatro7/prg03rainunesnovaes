/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioNaoEncontradoException;
import br.com.ifba.usuario.service.UsuarioService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rainu
 */
public class TelaListagemUsuario extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaListagemUsuario.class.getName());

    private static final int COLUNA_EDITAR = 3;
    private static final int COLUNA_REMOVER = 4;

    private final UsuarioService service = new UsuarioService();
    private List<Usuario> usuariosNaTabela = new ArrayList<>(); // mesma ordem das linhas da tabela

    // tabela so para leitura, o clique em Editar ou Remover e tratado no mouse
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Nome", "Login", "CPF", "Editar", "Remover"}, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    /**
     * Creates new form TelaListagemUsuario
     */
    public TelaListagemUsuario() {
        initComponents();
        configurarTela();
        carregarTabela();
    }

    // liga os componentes criados no GUI Builder com a logica da tela
    private void configurarTela() {
        txtBusca.setText("");
        txtBusca.setToolTipText("Digite parte do nome");
        tblUsuarios.setModel(modelo);
        btnBuscar.addActionListener(evt -> carregarTabela());
        txtBusca.addActionListener(evt -> carregarTabela());
        btnNovoUsuario.addActionListener(evt -> new TelaCadastroUsuario(this::carregarTabela).setVisible(true));
        tblUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int linha = tblUsuarios.rowAtPoint(evt.getPoint());
                int coluna = tblUsuarios.columnAtPoint(evt.getPoint());
                if (linha < 0) {
                    return;
                }
                Usuario usuario = usuariosNaTabela.get(linha);
                if (coluna == COLUNA_EDITAR) {
                    abrirEdicao(usuario);
                } else if (coluna == COLUNA_REMOVER) {
                    remover(usuario);
                }
            }
        });
    }

    // busca no banco usando o texto digitado (vazio traz todos)
    private void carregarTabela() {
        try {
            usuariosNaTabela = service.buscarPorNome(txtBusca.getText());
            modelo.setRowCount(0);
            for (Usuario usuario : usuariosNaTabela) {
                modelo.addRow(new Object[]{usuario.getNome(), usuario.getLogin(), usuario.getCpf(), "Editar", "Remover"});
            }
        } catch (RuntimeException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao listar usuarios", ex);
            JOptionPane.showMessageDialog(this, "Nao foi possivel carregar os usuarios do banco.\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirEdicao(Usuario usuario) {
        try {
            new TelaEdicaoUsuario(usuario.getId(), this::carregarTabela).setVisible(true);
        } catch (UsuarioNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            carregarTabela();
        } catch (RuntimeException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao abrir edicao", ex);
            JOptionPane.showMessageDialog(this, "Nao foi possivel abrir o usuario.\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover(Usuario usuario) {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja excluir o usuario " + usuario.getNome() + "?", "Confirmar exclusao", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            service.remover(usuario.getId());
            JOptionPane.showMessageDialog(this, "Usuario removido.");
        } catch (UsuarioNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao remover usuario", ex);
            JOptionPane.showMessageDialog(this, "Nao foi possivel remover o usuario.\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
        carregarTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBusca = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnNovoUsuario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtBusca.setText("jTextField1");

        btnBuscar.setText("Buscar");

        btnNovoUsuario.setText("+ Novo Usuário");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "Login", "CPF", "Editar", "Remover"
            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovoUsuario)
                        .addGap(53, 53, 53))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNovoUsuario))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaListagemUsuario().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnNovoUsuario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBusca;
    // End of variables declaration//GEN-END:variables
}
