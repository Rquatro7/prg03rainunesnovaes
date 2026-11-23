package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

// sabe transformar uma lista de usuarios nas linhas da tabela da listagem
// antes isso ficava dentro da TelaListagemUsuario, misturado com botoes e mensagens
public class UsuarioTableModel extends AbstractTableModel {

    public static final int COLUNA_EDITAR = 3;
    public static final int COLUNA_REMOVER = 4;

    private static final String[] COLUNAS = {"Nome", "Login", "CPF", "Editar", "Remover"};

    private List<Usuario> usuarios = new ArrayList<>();

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = new ArrayList<>(usuarios);
        fireTableDataChanged();
    }

    public Usuario getUsuario(int linha) {
        return usuarios.get(linha);
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return COLUNAS[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Usuario usuario = usuarios.get(linha);
        return switch (coluna) {
            case 0 -> usuario.getNome();
            case 1 -> usuario.getLogin();
            case 2 -> usuario.getCpf();
            case COLUNA_EDITAR -> "Editar";
            default -> "Remover";
        };
    }

    // a tabela e so para leitura, o clique em Editar e Remover e tratado pela tela
    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}
