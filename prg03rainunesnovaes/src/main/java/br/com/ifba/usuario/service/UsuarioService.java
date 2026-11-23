package br.com.ifba.usuario.service;

import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import java.util.List;

// regras de negocio do usuario, as telas falam com essa classe e nao com o dao direto
public class UsuarioService {

    private final UsuarioIDao dao;

    // construtor usado pelas telas, com o dao real do banco
    public UsuarioService() {
        this(new UsuarioDao());
    }

    // construtor que recebe o dao, usado nos testes com um dao falso
    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    // cadastra um usuario novo, impedindo login repetido
    public void cadastrar(Usuario usuario) {
        if (dao.existeLogin(usuario.getLogin())) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        dao.salvar(usuario);
    }

    // busca o usuario pelo login e confere a senha
    // se o login nao existe, o dao lanca UsuarioNaoEncontradoException
    public boolean autenticar(String login, String senha) {
        Usuario usuario = dao.buscarPorLogin(login);
        return usuario.autenticar(login, senha);
    }

    public Usuario atualizar(Usuario usuario) {
        return dao.atualizar(usuario);
    }

    public void remover(Long id) {
        dao.remover(id);
    }

    public Usuario buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    // texto vazio traz todos, senao filtra pelo nome
    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return dao.listarTodos();
        }
        return dao.buscarPorNome(nome);
    }
}
