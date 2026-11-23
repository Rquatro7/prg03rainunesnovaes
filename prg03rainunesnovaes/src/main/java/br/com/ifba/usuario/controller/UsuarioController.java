package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;

// recebe o pedido da tela e encaminha para a service, sem regra de negocio aqui
public class UsuarioController implements UsuarioIController {

    private final UsuarioIService service;

    public UsuarioController(UsuarioIService service) {
        this.service = service;
    }

    @Override
    public void salvar(Usuario usuario, String confirmarSenha) {
        service.cadastrar(usuario, confirmarSenha);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        return service.atualizar(usuario);
    }

    @Override
    public void excluir(Long id) {
        service.remover(id);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return service.buscarPorId(id);
    }

    @Override
    public List<Usuario> listar(String filtroNome) {
        return service.buscarPorNome(filtroNome);
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return service.autenticar(login, senha);
    }
}
