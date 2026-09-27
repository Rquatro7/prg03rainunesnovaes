package br.com.ifba.produto.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    @Test
    public void getCategoria_deveDevolverCategoriaDefinida() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new ProdutoShopee();

        produto.setCategoria(categoria);

        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    public void produto_deveNascerComPlataformaCorreta_quandoCriadoComConstrutor() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new ProdutoShopee(1, "Fone", "http://link.com", 50.0, categoria, "http://api.shopee.com/fone");

        assertEquals(PlataformaOrigem.SHOPEE, produto.getPlataforma());
    }

    @Test
    public void getNome_deveDevolverNomeDefinidoPeloSetter() {
        Produto produto = new ProdutoShopee();
        produto.setNome("Smartwatch");

        assertEquals("Smartwatch", produto.getNome());
    }

    // ---------- Task 02: comportamento herdado ----------

    @Test
    public void produtoShopee_deveUsarGetNomeHerdadoDeProduto() {
        // getNome() e herdado de Produto, nao existe implementacao propria em ProdutoShopee
        ProdutoShopee produto = new ProdutoShopee();
        produto.setNome("Fone Bluetooth");

        assertEquals("Fone Bluetooth", produto.getNome());
    }

    @Test
    public void produtoMercadoLivre_deveUsarGetLinkHerdadoDeProduto() {
        // getLink() e herdado de Produto, nao existe implementacao propria em ProdutoMercadoLivre
        ProdutoMercadoLivre produto = new ProdutoMercadoLivre();
        produto.setLink("http://mercadolivre.com/produto");

        assertEquals("http://mercadolivre.com/produto", produto.getLink());
    }

    // ---------- Task 02: comportamento sobrescrito ----------

    @Test
    public void atualizarPreco_produtoShopee_deveDevolverMensagemPropriaDaShopee() {
        ProdutoShopee produto = new ProdutoShopee();
        produto.setNome("Fone Bluetooth");

        assertEquals("Preco de Fone Bluetooth atualizado via API da Shopee.", produto.atualizarPreco());
    }

    @Test
    public void atualizarPreco_produtoMercadoLivre_deveDevolverMensagemPropriaDoMercadoLivre() {
        ProdutoMercadoLivre produto = new ProdutoMercadoLivre();
        produto.setNome("Panela Eletrica");

        assertEquals("Preco de Panela Eletrica atualizado via web scraping no Mercado Livre.", produto.atualizarPreco());
    }
}