package org.mbari.kb.jpa.knowledgebase;

import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.persistence.config.TargetDatabase;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Schlining
 * @since 2016-11-16T11:08:00
 */
public class DerbyTestDAOFactory {

    private static Config config = ConfigFactory.load();
    private static final Map<String, String> productionProps = ImmutableMap.<String, String>builder()
            .put("eclipselink.connection-pool.default.initial", "2")
            .put("eclipselink.connection-pool.default.max", "16")
            .put("eclipselink.connection-pool.default.min", "2")
            .put("eclipselink.logging.level", "INFO")
            .put("eclipselink.logging.session", "false")
            .put("eclipselink.logging.thread", "false")
            .put("eclipselink.logging.timestamp", "false")
            .put("eclipselink.target-database", TargetDatabase.Derby)
            .put("javax.persistence.database-product-name", TargetDatabase.Derby)
            .put("javax.persistence.schema-generation.database.action", "create")
            .put("javax.persistence.schema-generation.scripts.action", "drop-and-create")
            .put("javax.persistence.schema-generation.scripts.create-target", "test-database-create.ddl")
            .put("javax.persistence.schema-generation.scripts.drop-target", "test-database-drop.ddl")
            .build();

    public static EntityManagerFactory newEntityManagerFactory() {
        String driver = config.getString("org.mbari.vars.knowledgebase.database.derby.driver");
        String url = config.getString("org.mbari.vars.knowledgebase.database.derby.url");
        String user = config.getString("org.mbari.vars.knowledgebase.database.derby.user");
        String password = config.getString("org.mbari.vars.knowledgebase.database.derby.password");

        Map<String, String> props = new HashMap<>(productionProps);
        props.put("javax.persistence.jdbc.url", url);
        props.put("javax.persistence.jdbc.user", user);
        props.put("javax.persistence.jdbc.password", password);
        props.put("javax.persistence.jdbc.driver", driver);
        return Persistence.createEntityManagerFactory("vars-jpa-knowledgebase", props);
    }
}
