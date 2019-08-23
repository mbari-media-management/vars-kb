package org.mbari.kb.jpa.knowledgebase;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.mbari.kb.core.*;
import org.mbari.kb.jpa.*;
import org.mbari.kb.core.knowledgebase.ConceptCache;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;

import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author Brian Schlining
 * @since 2019-08-21T14:33:00
 */
public class Factories {

    private final EntityManagerFactory entityManagerFactory;
    private final KnowledgebaseDAOFactory knowledgebaseDAOFactory;
    private final MiscDAOFactory miscDAOFactory;
    private final PersistenceCache persistenceCache;


    public Factories(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        knowledgebaseDAOFactory = new KnowledgebaseDAOFactoryImpl(entityManagerFactory);
        miscDAOFactory = new MiscDAOFactoryImpl(entityManagerFactory);
        persistenceCache = new PersistenceCache(getPersistenceCacheProvider());
    }

    public ToolBelt getToolBelt() {
        return new ToolBelt(getKnowledgebaseDAOFactory(),
                getKnowledgebaseFactory(),
                getMiscDAOFactory(),
                getMiscFactory(),
                getPersistenceCacheProvider(),
                getConceptCache());
    }


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public KnowledgebaseDAOFactory getKnowledgebaseDAOFactory() {
        return knowledgebaseDAOFactory;
    }

    public MiscDAOFactory getMiscDAOFactory() {
        return miscDAOFactory;
    }

    public KnowledgebaseFactory getKnowledgebaseFactory() {
        return new KnowledgebaseFactoryImpl();
    }

    public MiscFactory getMiscFactory() {
        return new MiscFactoryImpl();
    }

    public PersistenceCacheProvider getPersistenceCacheProvider() {
        return new JPACacheProvider(entityManagerFactory, entityManagerFactory);
    }

    public VarsUserPreferencesFactory getVarsUserPreferencesFactory() {
        return new VarsUserPreferencesFactoryImpl(entityManagerFactory);
    }

    public ConceptCache getConceptCache() {
        return new ConceptCacheImpl(getKnowledgebaseDAOFactory(),
                persistenceCache);
    }

    public PersistenceCache getPersistenceCache() {
        return persistenceCache;
    }

    public SimpleDateFormat getDateFormatISO() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") {{
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }};
    }

    /**
     * Builds the factories for a given environment
     * @param environment Can be 'production' or anything else (although
     *                    'development' is expected.
     * @return
     */
    public static Factories build(String environment) {
        String nodeName = environment.equalsIgnoreCase("production") ?
                "org.mbari.vars.knowledgebase.database.production" :
                "org.mbari.vars.knowledgebase.database.development";

        EntityManagerFactory entityManagerFactory =
                EntityManagerFactories.newEntityManagerFactory(nodeName);
        return new Factories(entityManagerFactory);
    }

    public static Factories build() {
        Config config = ConfigFactory.load();
        String environment = config.getString("database.environment");
        return build(environment);
    }
}
