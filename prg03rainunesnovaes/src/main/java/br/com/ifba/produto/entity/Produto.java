package br.com.ifba.produto.entity;

// classe de dominio que representa um produto monitorado pelo bot
public class Produto {

    private int id;
    private String nome;
    private String link;
    private double precoReferencia;
    private Categoria categoria; // objeto como atributo (relacionamento 1:1)
    private PlataformaOrigem plataforma;

    public Produto() {
    }

    public Produto(int id, String nome, String link, double precoReferencia, Categoria categoria, PlataformaOrigem plataforma) {
        this.id = id;
        this.nome = nome;
        this.link = link;
        this.precoReferencia = precoReferencia;
        this.categoria = categoria;
        this.plataforma = plataforma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPrecoReferencia() {
        return precoReferencia;
    }

    public void setPrecoReferencia(double precoReferencia) {
        this.precoReferencia = precoReferencia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public PlataformaOrigem getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaOrigem plataforma) {
        this.plataforma = plataforma;
    }
}