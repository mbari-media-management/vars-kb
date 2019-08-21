package vars.jpa;


import javax.persistence.EntityManagerFactory;

/**
 * @author Brian Schlining
 * @since 2016-11-22T10:50:00
 */
public class DevelopmentDAOFactory {

    public static EntityManagerFactory newEntityManagerFactory() {

        String nodeName = "org.mbari.vars.knowledgebase.database.development";

        return EntityManagerFactories.newEntityManagerFactory(nodeName);

    }
}
