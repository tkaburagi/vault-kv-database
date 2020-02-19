package run.kabuctl.demo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import run.kabuctl.demo.entity.Secrets;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    VaultTemplate vaultTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public List<String> getSecrets() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        VaultResponseSupport<Secrets> vaultResponse = vaultTemplate.read("secret/application/vault-kv-demo", Secrets.class);
        try {
            String json = mapper.writeValueAsString(vaultResponse.getData());
            System.out.println(json);
            List<String> list = new ArrayList();
            list.add(mapper.writeValueAsString(vaultResponse.getData().getUsername()));
            list.add(mapper.writeValueAsString(vaultResponse.getData().getPassword()));
            System.out.println(list.get(0) + " : " +  list.get(1));
            return list;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    @Bean
    public DataSource datasource() {
        DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/handson?useSSL=false");

        List<String> list = getSecrets();
        ds.setUsername(list.get(0).replace("\"", ""));
        ds.setPassword(list.get(1).replace("\"", ""));
        return ds;
    }

}
