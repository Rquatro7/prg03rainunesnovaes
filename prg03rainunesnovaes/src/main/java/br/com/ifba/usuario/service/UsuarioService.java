package br.com.ifba.usuario.service;

import org.springframework.stereotype.Service;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.LoginDuplicadoException;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
import br.com.ifba.usuario.validar.ValidadorUsuario;
import java.util.List;

// regras de negocio do usuario. As validacoes que antes ficavam no botao da tela vieram para ca
@Service
public class UsuarioService implements UsuarioIService {

    private final UsuarioIDao dao;

    // o dao chega pronto pelo construtor, a service nao cria a propria dependencia
    public UsuarioService(UsuarioIDao dao) {
        this.dao = dao;
    }

    @Override
    public void cadastrar(Usuario usuario, String confirmarSenha) {
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getCpf(), usuario.getDataNascimento(),
                usuario.getTelefone(), usuario.getEmail(), usuario.getLogin(), usuario.getSenha(), confirmarSenha)) {
            throw new UsuarioInvalidoException("Preencha todos os campos.");
        }
        validarCpf(usuario.getCpf());
        if (!ValidadorUsuario.senhasCoincidem(usuario.getSenha(), confirmarSenha)) {
            throw new UsuarioInvalidoException("As senhas não coincidem.");
        }
        if (!ValidadorUsuario.senhaTemTamanhoMinimo(usuario.getSenha())) {
            throw new UsuarioInvalidoException("A senha deve ter pelo menos 6 caracteres.");
        }
        if (ValidadorUsuario.contemPalavraProibida(usuario.getLogin())) {
            throw new UsuarioInvalidoException("Login contém palavra não permitida.");
        }
        if (dao.existsByLogin(usuario.getLogin())) {
            throw new LoginDuplicadoException(usuario.getLogin());
        }
        dao.save(usuario);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        if (!ValidadorUsuario.camposPreenchidos(usuario.getNome(), usuario.getCpf())) {
            throw new UsuarioInvalidoException("Nome e CPF são obrigatórios.");
        }
        validarCpf(usuario.getCpf());
        return dao.update(usuario);
    }

    // busca antes para avisar com UsuarioNaoEncontradoException se o id nao existir mais
    @Override
    public void remover(Long id) {
        dao.delete(dao.findById(id));
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return dao.findById(id);
    }

    // texto vazio traz todos, senao filtra pelo nome
    @Override
    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return dao.findAll();
        }
        return dao.findByNome(nome);
    }

    // se o login nao existe, o dao lanca UsuarioNaoEncontradoException
    @Override
    public boolean autenticar(String login, String senha) {
        Usuario usuario = dao.findByLogin(login);
        return usuario.autenticar(login, senha);
    }

    private void validarCpf(String cpf) {
        if (!ValidadorUsuario.cpfValido(cpf)) {
            throw new UsuarioInvalidoException("CPF inválido.");
        }
    }
}
