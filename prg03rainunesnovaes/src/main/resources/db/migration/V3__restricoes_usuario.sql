-- login identifica o usuario (equals/hashCode usam ele), entao nao pode repetir nem ficar vazio
ALTER TABLE usuario MODIFY nome  VARCHAR(100) NOT NULL;
ALTER TABLE usuario MODIFY login VARCHAR(50)  NOT NULL;
ALTER TABLE usuario MODIFY senha VARCHAR(100) NOT NULL;
ALTER TABLE usuario ADD CONSTRAINT uk_usuario_login UNIQUE (login);
