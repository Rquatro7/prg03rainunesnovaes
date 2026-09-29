package br.com.ifba;

import br.com.ifba.usuario.interfaces.Autenticavel;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.produto.entity.Produto;
import br.com.ifba.produto.entity.ProdutoShopee;
import br.com.ifba.produto.entity.ProdutoMercadoLivre;
import br.com.ifba.produto.entity.Categoria;

public class Polimorfismo {

    // metodo que recebe o tipo geral (interface), nao o tipo concreto
    public static String processarAutenticacao(Autenticavel pessoa, String login, String senha) {
        return pessoa.autenticar(login, senha) ? "Acesso liberado" : "Acesso negado";
    }

    // metodo que recebe o tipo geral (superclasse abstrata), nao o tipo concreto
    public static String processarAtualizacao(Produto produto) {
        return produto.atualizarPreco();
    }

    public static void main(String[] args) {
        // Task 01 e 03: interface Autenticavel
        Usuario usuario = new Usuario();
        usuario.setLogin("rainunes");
        usuario.setSenha("123456");

        System.out.println(processarAutenticacao(usuario, "rainunes", "123456"));
        System.out.println(processarAutenticacao(usuario, "rainunes", "senhaerrada"));

        // Task 01 e 03: superclasse abstrata Produto
        Categoria categoria = new Categoria(1, "Eletronicos");
        Produto shopee = new ProdutoShopee(1, "Fone", "http://link.com", 50.0, categoria, "http://api.shopee.com/fone");
        Produto ml = new ProdutoMercadoLivre(2, "Panela", "http://link2.com", 150.0, categoria, "#preco");

        System.out.println(processarAtualizacao(shopee));
        System.out.println(processarAtualizacao(ml));
    }
}