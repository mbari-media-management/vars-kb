package vars.jpa;

import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Schlining
 * @since 2016-11-16T14:33:00
 */
public class DevelopmentDAOFactory {

    private static Config config = ConfigFactory.load();
    private static final String productName = config.getString("org.mbari.vars.knowledgebase.database.development.name");
    private static final Map<String, String> productionProps = ImmutableMap.<String, String>builder()
            .put("eclipselink.connection-pool.default.initial", "2")
            .put("eclipselink.connection-pool.default.max", "16")
            .put("eclipselink.connection-pool.default.min", "2")
            .put("eclipselink.logging.level", "INFO")
            .put("eclipselink.logging.session", "false")
            .put("eclipselink.logging.thread", "false")
            .put("eclipselink.logging.timestamp", "false")
            .put("eclipselink.target-database", productName)
            .put("javax.persistence.database-product-name", productName)
            .put("javax.persistence.schema-generation.database.action", "create")
            .build();

    public static EntityManagerFactory newEntityManagerFactory() {
        String driver = config.getString("org.mbari.vars.knowledgebase.database.development.driver");
        String url = config.getString("org.mbari.vars.knowledgebase.database.development.url");
        String user = config.getString("org.mbari.vars.knowledgebase.database.development.user");
        String password = config.getString("org.mbari.vars.knowledgebase.database.development.password");

        Map<String, String> props = new HashMap<>(productionProps);
        props.put("javax.persistence.jdbc.url", url);
        props.put("javax.persistence.jdbc.user", user);
        props.put("javax.persistence.jdbc.password", password);
        props.put("javax.persistence.jdbc.driver", driver);
        return Persistence.createEntityManagerFactory("vars-jpa-knowledgebase", props);
    }
}