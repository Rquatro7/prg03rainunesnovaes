package br.com.ifba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.interfaces.Autenticavel;
import br.com.ifba.produto.entity.Produto;
import br.com.ifba.produto.entity.ProdutoShopee;
import br.com.ifba.produto.entity.ProdutoMercadoLivre;
import br.com.ifba.produto.entity.Categoria;

public class PolimorfismoTest {

    // ---------- polimorfismo via interface Autenticavel ----------

    @Test
    public void processarAutenticacao_deveLiberarAcesso_quandoCredenciaisCorretas() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        Autenticavel pessoa = usuario; // tipo geral, nao o concreto

        assertEquals("Acesso liberado", Polimorfismo.processarAutenticacao(pessoa, "rainunes", "123456"));
    }

    @Test
    public void processarAutenticacao_deveNegarAcesso_quandoSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        Autenticavel pessoa = usuario;

        assertEquals("Acesso negado", Polimorfismo.processarAutenticacao(pessoa, "rainunes", "senhaerrada"));
    }

    // ---------- polimorfismo via superclasse abstrata Produto ----------

    @Test
    public void processarAtualizacao_deveUsarComportamentoDaShopee_quandoRecebeProdutoShopee() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new ProdutoShopee(1, "Fone", "http://link.com", 50.0, categoria, "http://api.shopee.com/fone");

        assertEquals("Preco de Fone atualizado via API da Shopee.", Polimorfismo.processarAtualizacao(produto));
    }

    @Test
    public void processarAtualizacao_deveUsarComportamentoDoMercadoLivre_quandoRecebeProdutoMercadoLivre() {
        Categoria categoria = new Categoria(2, "Casa");
        Produto produto = new ProdutoMercadoLivre(2, "Panela", "http://link2.com", 150.0, categoria, "#preco");

        assertEquals("Preco de Panela atualizado via web scraping no Mercado Livre.", Polimorfismo.processarAtualizacao(produto));
    }

    @Test
    public void processarAtualizacao_deveDevolverSaidasDiferentes_paraSubclassesDiferentes() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto shopee = new ProdutoShopee(1, "Fone", "http://link.com", 50.0, categoria, "http://api.shopee.com/fone");
        Produto ml = new ProdutoMercadoLivre(2, "Panela", "http://link2.com", 150.0, categoria, "#preco");

        String resultadoShopee = Polimorfismo.processarAtualizacao(shopee);
        String resultadoMl = Polimorfismo.processarAtualizacao(ml);

        assertNotEquals(resultadoShopee, resultadoMl);
    }
}