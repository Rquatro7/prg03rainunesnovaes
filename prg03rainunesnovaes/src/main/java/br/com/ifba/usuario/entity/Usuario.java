package br.com.ifba.usuario.entity;
import br.com.ifba.usuario.interfaces.Autenticavel;
import br.com.ifba.produto.entity.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// classe de dominio que representa um usuario do sistema
@Entity
@Table(name = "usuario")
public class Usuario implements Autenticavel {

    // chave primaria, gerada automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos privados (encapsulamento)
    private String nome;
    private String cpf;
    private String genero;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    private String telefone;
    private String email;
    private String login;
    private String senha;

    // relacionamento ainda nao mapeado como entidade JPA (Produto nao e @Entity ainda),
    // entao o hibernate ignora esse campo ao persistir Usuario
    @Transient
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // dois usuarios sao considerados iguais quando tem o mesmo login,
    // ja que o login e o identificador usado para autenticar e buscar o usuario no sistema
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario outro = (Usuario) obj;
        return Objects.equals(this.login, outro.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}