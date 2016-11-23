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
 * @since 2016-11-21T11:43:00
 */
public class EntityManagerFactories {

    private static Config config = ConfigFactory.load();
    private static final Map<String, String> productionProps = ImmutableMap.<String, String>builder()
            .put("eclipselink.connection-pool.default.initial", "2")
            .put("eclipselink.connection-pool.default.max", "16")
            .put("eclipselink.connection-pool.default.min", "2")
            .put("eclipselink.logging.level", "INFO")
            .put("eclipselink.logging.session", "false")
            .put("eclipselink.logging.thread", "false")
            .put("eclipselink.logging.timestamp", "false")
            .put("javax.persistence.schema-generation.database.action", "create")
            .build();

    public static EntityManagerFactory newEntityManagerFactory(String configNode) {

        String driver = config.getString(configNode + ".driver");
        String url = config.getString(configNode + ".url");
        String user = config.getString(configNode + ".user");
        String password = config.getString(configNode + ".password");
        String productName = config.getString(configNode + ".name");

        Map<String, String> props = new HashMap<>(productionProps);
        props.put("javax.persistence.jdbc.url", url);
        props.put("javax.persistence.jdbc.user", user);
        props.put("javax.persistence.jdbc.password", password);
        props.put("javax.persistence.jdbc.driver", driver);
        props.put("eclipselink.target-database", productName);
        props.put("javax.persistence.database-product-name", productName);

        return Persistence.createEntityManagerFactory("vars-jpa-knowledgebase", props);
    }
}
