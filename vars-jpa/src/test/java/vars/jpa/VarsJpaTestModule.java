package vars.jpa;

import vars.knowledgebase.jpa.DerbyTestDAOFactory;
import vars.knowledgebase.jpa.H2TestDAOFactory;

/**
 * This Guice Module sets up the Factories needed
 */
public class VarsJpaTestModule extends VarsJpaModule {

    public VarsJpaTestModule() {
        super(DerbyTestDAOFactory.newEntityManagerFactory());
        //super(H2TestDAOFactory.newEntityManagerFactory());
    }

}
