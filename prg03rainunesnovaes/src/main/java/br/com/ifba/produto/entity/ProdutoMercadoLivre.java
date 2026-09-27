package br.com.ifba.produto.entity;

// produto cuja coleta de preco e feita via web scraping no Mercado Livre
public class ProdutoMercadoLivre extends Produto {

    private String seletorScraping;

    public ProdutoMercadoLivre() {
        super();
    }

    public ProdutoMercadoLivre(int id, String nome, String link, double precoReferencia, Categoria categoria, String seletorScraping) {
        super(id, nome, link, precoReferencia, categoria, PlataformaOrigem.MERCADO_LIVRE);
        this.seletorScraping = seletorScraping;
    }

    public String getSeletorScraping() {
        return seletorScraping;
    }

    public void setSeletorScraping(String seletorScraping) {
        this.seletorScraping = seletorScraping;
    }

    // sobrescreve o metodo da classe mae, usando web scraping
    @Override
    public String atualizarPreco() {
        return "Preco de " + getNome() + " atualizado via web scraping no Mercado Livre.";
    }
}