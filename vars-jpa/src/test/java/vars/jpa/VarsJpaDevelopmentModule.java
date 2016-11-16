package vars.jpa;

import vars.knowledgebase.jpa.DevelopmentDAOFactory;

public class VarsJpaDevelopmentModule extends VarsJpaModule {
    

    public VarsJpaDevelopmentModule() {
        super(DevelopmentDAOFactory.newEntityManagerFactory());
    }
}