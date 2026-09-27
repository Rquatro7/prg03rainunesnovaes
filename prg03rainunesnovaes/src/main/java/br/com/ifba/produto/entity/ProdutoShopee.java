package br.com.ifba.produto.entity;

// produto cuja coleta de preco e feita via API oficial da Shopee
public class ProdutoShopee extends Produto {

    private String urlApiShopee;

    public ProdutoShopee() {
        super();
    }

    public ProdutoShopee(int id, String nome, String link, double precoReferencia, Categoria categoria, String urlApiShopee) {
        super(id, nome, link, precoReferencia, categoria, PlataformaOrigem.SHOPEE);
        this.urlApiShopee = urlApiShopee;
    }

    public String getUrlApiShopee() {
        return urlApiShopee;
    }

    public void setUrlApiShopee(String urlApiShopee) {
        this.urlApiShopee = urlApiShopee;
    }

    // sobrescreve o metodo da classe mae, usando a API da Shopee
    @Override
    public String atualizarPreco() {
        return "Preco de " + getNome() + " atualizado via API da Shopee.";
    }
}