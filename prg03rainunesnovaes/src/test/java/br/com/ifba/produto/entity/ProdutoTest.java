package br.com.ifba.produto.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    @Test
    public void getCategoria_deveDevolverCategoriaDefinida() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new Produto();

        produto.setCategoria(categoria);

        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    public void produto_deveNascerComPlataformaCorreta_quandoCriadoComConstrutor() {
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto produto = new Produto(1, "Fone", "http://link.com", 50.0, categoria, PlataformaOrigem.SHOPEE);

        assertEquals(PlataformaOrigem.SHOPEE, produto.getPlataforma());
    }

    @Test
    public void getNome_deveDevolverNomeDefinidoPeloSetter() {
        Produto produto = new Produto();
        produto.setNome("Smartwatch");

        assertEquals("Smartwatch", produto.getNome());
    }
}