package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;
import br.com.ifba.produto.entity.Produto;
import java.util.ArrayList;
import java.util.List;

// classe de dominio que representa um usuario do sistema
public class Usuario implements Autenticavel {

    // atributos privados (encapsulamento)
    private String nome;
    private String cpf;
    private String genero;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String login;
    private String senha;
    private List<Produto> produtosMonitorados; // relacionamento 1..N

    // construtor vazio
    public Usuario() {
        this.produtosMonitorados = new ArrayList<>();
    }

    // construtor com os atributos principais
    public Usuario(String nome, String cpf, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.login = login;
        this.senha = senha;
        this.produtosMonitorados = new ArrayList<>();
    }

    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // retorna uma copia da lista, para nao expor a lista original
    public List<Produto> getProdutosMonitorados() {
        return new ArrayList<>(produtosMonitorados);
    }

    // metodo que adiciona um produto a lista, sem expor a lista para fora
    public void adicionarProduto(Produto produto) {
        this.produtosMonitorados.add(produto);
    }

    // implementacao do metodo da interface Autenticavel
    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }
}