package vars.jpa;

public class VarsJpaDevelopmentModule extends VarsJpaModule {
    

    public VarsJpaDevelopmentModule() {
        super(DevelopmentDAOFactory.newEntityManagerFactory());
    }
}