package br.com.ifba.usuario.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.ifba.produto.entity.Categoria;
import br.com.ifba.produto.entity.Produto;
import br.com.ifba.produto.entity.ProdutoShopee;
import br.com.ifba.produto.entity.ProdutoMercadoLivre;

public class UsuarioTest {

    @Test
    public void autenticar_deveRetornarTrue_quandoCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        assertTrue(usuario.autenticar("rainunes", "123456"));
    }

    @Test
    public void autenticar_deveRetornarFalse_quandoSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        assertFalse(usuario.autenticar("rainunes", "senhaerrada"));
    }

    @Test
    public void adicionarProduto_deveAumentarTamanhoDaLista() {
        Usuario usuario = new Usuario();
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new ProdutoShopee(1, "Fone Bluetooth", "http://link.com", 99.90, categoria, "http://api.shopee.com/fone");

        usuario.adicionarProduto(produto);

        assertEquals(1, usuario.getProdutosMonitorados().size());
    }

    @Test
    public void getProdutosMonitorados_deveRetornarListaVazia_quandoUsuarioRecemCriado() {
        Usuario usuario = new Usuario();

        assertTrue(usuario.getProdutosMonitorados().isEmpty());
    }

    @Test
    public void getProdutosMonitorados_deveDevolverProdutoAdicionado() {
        Usuario usuario = new Usuario();
        Categoria categoria = new Categoria(2, "Casa");
        Produto produto = new ProdutoMercadoLivre(2, "Panela Eletrica", "http://link2.com", 150.00, categoria, "#preco-panela");

        usuario.adicionarProduto(produto);

        assertEquals(produto, usuario.getProdutosMonitorados().get(0));
    }
}