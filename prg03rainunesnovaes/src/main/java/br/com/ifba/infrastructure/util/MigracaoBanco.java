package br.com.ifba.infrastructure.util;

import org.flywaydb.core.Flyway;

// roda as migrations do Flyway (pasta db/migration) antes do Hibernate subir
// os dados de conexao vem da ConfiguracaoConexao, aqui so se cuida de migrar
public class MigracaoBanco {

    private static boolean jaMigrou = false;

    private MigracaoBanco() {
    }

    // aplica as migrations pendentes uma vez so por execucao
    public static synchronized void migrar() {
        if (jaMigrou) {
            return;
        }
        ConfiguracaoConexao conexao = ConfiguracaoConexao.lerDoPersistenceXml();
        Flyway.configure()
                .dataSource(conexao.getUrl(), conexao.getUsuario(), conexao.getSenha())
                .locations("classpath:db/migration")
                .load()
                .migrate();
        jaMigrou = true;
    }
}
