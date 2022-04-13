package org.mbari.kb.jpa;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Schlining
 * @since 2016-11-21T11:43:00
 */
public class EntityManagerFactories {

    private static Config config = ConfigFactory.load();
    private static final Map<String, String> productionProps = Map.of(
            "eclipselink.connection-pool.default.initial", "2",
            "eclipselink.connection-pool.default.max", "16",
            "eclipselink.connection-pool.default.min", "2",
            "eclipselink.logging.session", "false",
            "eclipselink.logging.thread", "false",
            "eclipselink.logging.timestamp", "false",
            "javax.persistence.schema-generation.database.action", "create"
    );

    public static EntityManagerFactory newEntityManagerFactory(String url,
                                                               String username,
                                                               String password,
                                                               String driverName,
                                                               String productName,
                                                               Map<String, String> properties) {
        Map<String, String> props = new HashMap<>(properties);
        props.put("javax.persistence.jdbc.url", url);
        props.put("javax.persistence.jdbc.user", username);
        props.put("javax.persistence.jdbc.password", password);
        props.put("javax.persistence.jdbc.driver", driverName);
        props.put("eclipselink.target-database", productName);
        props.put("javax.persistence.database-product-name", productName);

        String logLevel = config.getString("database.loglevel");
        props.put("eclipselink.logging.level", logLevel);

       return newEntityManagerFactory(props);
    }

    private static EntityManagerFactory newEntityManagerFactory(Map<String, String> properties) {
        Map<String, String> props = new HashMap<>(productionProps);
        props.putAll(properties);
        return Persistence.createEntityManagerFactory("vars-jpa-knowledgebase", props);
    }


    public static EntityManagerFactory newEntityManagerFactory(String configNode) {
        String driver = config.getString(configNode + ".driver");
        String url = config.getString(configNode + ".url");
        String user = config.getString(configNode + ".user");
        String password = config.getString(configNode + ".password");
        String productName = config.getString(configNode + ".name");

       return newEntityManagerFactory(url, user, password, driver, productName, Collections.emptyMap());
    }
}
