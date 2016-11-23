package vars.jpa;

import com.google.inject.Injector;
import com.google.inject.Guice;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 11, 2009
 * Time: 9:56:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DaoFactoryTest {

    @Test
    public void test01() {
        Injector injector = Guice.createInjector(new VarsJpaTestModule());
        KnowledgebaseDAOFactory kf = injector.getInstance(KnowledgebaseDAOFactory.class);
        assertNotNull(kf.newConceptDAO());
        assertNotNull(kf.newConceptMetadataDAO());
        assertNotNull(kf.newConceptNameDAO());
        assertNotNull(kf.newHistoryDAO());
        assertNotNull(kf.newLinkRealizationDAO());
        assertNotNull(kf.newLinkTemplateDAO());
        assertNotNull(kf.newMediaDAO());
    }

}
