# prg03rainunes
Projeto POO - Curso ADS IFBA

Atividade 1: Documentação do projeto
Atividade 2: Aprender GIT
Atividade 3: LearnGit e Pull Request












## Sobrecarga de construtores (Usuario)

O construtor vazio `Usuario()` existe para os casos em que os dados serão preenchidos aos poucos (como na Tela de Login, que só precisa de login e senha). Já o construtor `Usuario(nome, cpf, login, senha)` serve para o cadastro completo, onde os dados obrigatórios já estão disponíveis de uma vez.

## Busca por login: List vs Map (RepositorioUsuarioEmMemoria)

Com dez usuários a diferença é imperceptível, mas o `for` na lista percorre, no pior caso, todos os usuários até achar o login (ou não achar), enquanto o `Map` acha direto pela chave. Com dez mil usuários isso importa: a busca na lista fica lenta porque cresce junto com a quantidade de usuários, mas a busca no Map continua rápida porque não depende do tamanho da base.
