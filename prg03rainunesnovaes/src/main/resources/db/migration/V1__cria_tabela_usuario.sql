-- tabela principal do projeto: os usuarios do PROMOBOT
CREATE TABLE usuario (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(100),
    cpf             VARCHAR(14),
    genero          VARCHAR(20),
    data_nascimento VARCHAR(10),
    telefone        VARCHAR(20),
    email           VARCHAR(100),
    login           VARCHAR(50),
    senha           VARCHAR(100),
    PRIMARY KEY (id)
) ENGINE = InnoDB;
