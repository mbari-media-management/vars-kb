package vars.jpa;

/**
 * @author Brian Schlining
 * @since 2016-11-22T10:34:00
 */
public class VarsJpaDevelopmentModule extends VarsJpaModule {

    static String nodeName = "org.mbari.vars.knowledgebase.database.development";

    public VarsJpaDevelopmentModule() {
        super(EntityManagerFactories.newEntityManagerFactory(nodeName));
    }

}