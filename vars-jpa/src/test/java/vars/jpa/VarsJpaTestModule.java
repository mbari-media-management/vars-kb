package vars.jpa;

import vars.knowledgebase.jpa.DerbyTestDAOFactory;

/**
 * This Guice Module sets up the Factories needed
 */
public class VarsJpaTestModule extends VarsJpaModule {


    public VarsJpaTestModule() {
        super(DerbyTestDAOFactory.newEntityManagerFactory());
    }

}
