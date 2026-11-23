# SOLID no PROMOBOT

Caminhos a partir de `prg03rainunesnovaes/src/main/java/br/com/ifba/`.

## Um exemplo de cada principio

**S - Responsabilidade unica**
`usuario/controller/UsuarioController.java`, linha 18. O controller so recebe o pedido da tela e repassa para a service. Ele nao valida nada e nao fala com o banco, entao so muda se o jeito da tela pedir as coisas mudar.

**O - Aberto/fechado**
`produto/entity/Produto.java`, linha 76 (`atualizarPreco()` abstrato). Para o bot monitorar uma plataforma nova (Amazon, por exemplo) basta criar outra subclasse de Produto. O `processarAtualizacao` do `Polimorfismo.java` (linha 18) continua igual, sem if de plataforma.

**L - Substituicao de Liskov**
`ProdutoShopee.java` e `ProdutoMercadoLivre.java`, linha 27. Qualquer uma das duas pode ir onde se espera um `Produto` e o programa continua funcionando. O mesmo acontece com o `FakeUsuarioDao` (nos testes) no lugar do `UsuarioDao`: a service nem percebe a troca.

**I - Segregacao de interface**
`usuario/interfaces/Autenticavel.java`, linha 5. A interface tem um metodo so, `autenticar`. Quem so precisa autenticar nao e obrigado a implementar mais nada. O mesmo vale para o `UsuarioIDao` (linha 8), que herda o CRUD basico e so acrescenta as buscas do usuario.

**D - Inversao de dependencia**
`usuario/service/UsuarioService.java`, linhas 13 e 16. A service depende da interface `UsuarioIDao`, que chega pelo construtor, e nao da classe `UsuarioDao`. Por isso os testes rodam com o fake, sem banco. O `UsuarioController` (linha 12) faz o mesmo com o `UsuarioIService`.

## Violacoes encontradas

1. **SRP na MigracaoBanco** (corrigida). Ela lia o persistence.xml na mao (abria o XML e procurava as propriedades) e tambem rodava o Flyway. Se mudasse o lugar da configuracao, mudava a classe de migracao.
2. **SRP na TelaListagemUsuario** (corrigida). Alem de cuidar dos botoes e das mensagens, a tela sabia transformar cada `Usuario` numa linha da tabela, com as colunas montadas na mao dentro do metodo de carregar.
3. **DIP na UsuarioService**, linhas 22, 27 e 30. Ela chama o `ValidadorUsuario` direto por metodos estaticos. Nao da para trocar a regra de validacao sem mexer na service. O certo seria uma interface de validador recebida pelo construtor, igual ao dao.
4. **SRP no JPAUtil**, linha 22. A classe que cria a fabrica do Hibernate tambem dispara as migrations. Sao duas tarefas, e ainda fica escondido que pedir um EntityManager pode rodar o Flyway.

## O que foi corrigido

- Violacao 1: a leitura dos dados de conexao foi para a classe nova `ConfiguracaoConexao`. A `MigracaoBanco` ficou so com o Flyway.
- Violacao 2: as colunas e as linhas da tabela foram para o `UsuarioTableModel`. A tela so chama `modeloTabela.setUsuarios(...)` (linha 76 da `TelaListagemUsuario`).
