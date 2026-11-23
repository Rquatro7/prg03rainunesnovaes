package br.com.ifba.infrastructure.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// dados de conexao com o banco, lidos do persistence.xml
// fica separado da MigracaoBanco porque saber ONDE estao os dados e saber MIGRAR sao coisas diferentes
public class ConfiguracaoConexao {

    private static final String ARQUIVO_PERSISTENCIA = "META-INF/persistence.xml";

    private final String url;
    private final String usuario;
    private final String senha;

    private ConfiguracaoConexao(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static ConfiguracaoConexao lerDoPersistenceXml() {
        Map<String, String> propriedades = lerPropriedades();
        return new ConfiguracaoConexao(
                propriedades.get("jakarta.persistence.jdbc.url"),
                propriedades.get("jakarta.persistence.jdbc.user"),
                propriedades.get("jakarta.persistence.jdbc.password"));
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    private static Map<String, String> lerPropriedades() {
        try (InputStream arquivo = ConfiguracaoConexao.class.getClassLoader().getResourceAsStream(ARQUIVO_PERSISTENCIA)) {
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
