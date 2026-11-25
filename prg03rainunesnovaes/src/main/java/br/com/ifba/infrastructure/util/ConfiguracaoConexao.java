package br.com.ifba.infrastructure.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// dados de conexao com o banco, lidos do application.properties
public class ConfiguracaoConexao {

    private static final String ARQUIVO = "application.properties";

    private final Properties propriedades;

    private ConfiguracaoConexao(Properties propriedades) {
        this.propriedades = propriedades;
    }

    public static ConfiguracaoConexao lerDoApplicationProperties() {
        try (InputStream arquivo = ConfiguracaoConexao.class.getClassLoader().getResourceAsStream(ARQUIVO)) {
            if (arquivo == null) {
                throw new IllegalStateException("Arquivo " + ARQUIVO
                        + " nao encontrado. Copie o application.properties.exemplo e preencha com os dados do seu banco.");
            }
            Properties propriedades = new Properties();
            propriedades.load(arquivo);
            return new ConfiguracaoConexao(propriedades);
        } catch (IllegalStateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Nao foi possivel ler o " + ARQUIVO, ex);
        }
    }

    public String getUrl() {
        return propriedades.getProperty("spring.datasource.url");
    }

    public String getUsuario() {
        return propriedades.getProperty("spring.datasource.username");
    }

    public String getSenha() {
        return propriedades.getProperty("spring.datasource.password");
    }

    // as mesmas configuracoes no formato que o Hibernate entende
    public Map<String, String> paraHibernate() {
        Map<String, String> jpa = new HashMap<>();
        jpa.put("jakarta.persistence.jdbc.url", getUrl());
        jpa.put("jakarta.persistence.jdbc.user", getUsuario());
        jpa.put("jakarta.persistence.jdbc.password", getSenha());
        jpa.put("jakarta.persistence.jdbc.driver", propriedades.getProperty("spring.datasource.driver-class-name"));
        jpa.put("hibernate.hbm2ddl.auto", propriedades.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpa.put("hibernate.show_sql", propriedades.getProperty("spring.jpa.show-sql"));
        jpa.put("hibernate.format_sql", propriedades.getProperty("spring.jpa.properties.hibernate.format_sql"));
        jpa.put("hibernate.dialect", propriedades.getProperty("spring.jpa.properties.hibernate.dialect"));
        return jpa;
    }
}
