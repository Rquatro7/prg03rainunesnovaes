package br.com.ifba.infrastructure.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.flywaydb.core.Flyway;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// roda as migrations do Flyway (pasta db/migration) antes do Hibernate subir
// os dados de conexao sao lidos do proprio persistence.xml, para nao repetir a senha em outro arquivo
public class MigracaoBanco {

    private static final String ARQUIVO_PERSISTENCIA = "META-INF/persistence.xml";
    private static boolean jaMigrou = false;

    private MigracaoBanco() {
    }

    // aplica as migrations pendentes uma vez so por execucao
    public static synchronized void migrar() {
        if (jaMigrou) {
            return;
        }
        Map<String, String> conexao = lerConexao();
        Flyway.configure()
                .dataSource(conexao.get("jakarta.persistence.jdbc.url"),
                        conexao.get("jakarta.persistence.jdbc.user"),
                        conexao.get("jakarta.persistence.jdbc.password"))
                .locations("classpath:db/migration")
                .load()
                .migrate();
        jaMigrou = true;
    }

    private static Map<String, String> lerConexao() {
        try (InputStream arquivo = MigracaoBanco.class.getClassLoader().getResourceAsStream(ARQUIVO_PERSISTENCIA)) {
            if (arquivo == null) {
                throw new IllegalStateException("Arquivo " + ARQUIVO_PERSISTENCIA
                        + " nao encontrado. Copie o persistence.xml.exemplo e preencha com os dados do seu banco.");
            }
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(arquivo);
            NodeList propriedades = documento.getElementsByTagName("property");
            Map<String, String> valores = new HashMap<>();
            for (int i = 0; i < propriedades.getLength(); i++) {
                Element propriedade = (Element) propriedades.item(i);
                valores.put(propriedade.getAttribute("name"), propriedade.getAttribute("value"));
            }
            return valores;
        } catch (IllegalStateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Nao foi possivel ler os dados de conexao do " + ARQUIVO_PERSISTENCIA, ex);
        }
    }
}
