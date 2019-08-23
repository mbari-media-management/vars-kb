package vars.jpa;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.jpa.DerbyTestDAOFactory;
import org.mbari.kb.jpa.knowledgebase.Factories;

/**
 * Created by IntelliJ IDEA. User: brian Date: Aug 11, 2009 Time: 9:56:33 AM To
 * change this template use File | Settings | File Templates.
 */
public class DaoFactoryTest {

    @Test
    public void test01() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        KnowledgebaseDAOFactory kf = factories.getKnowledgebaseDAOFactory();
        assertNotNull(kf.newConceptDAO());
        assertNotNull(kf.newConceptMetadataDAO());
        assertNotNull(kf.newConceptNameDAO());
        assertNotNull(kf.newHistoryDAO());
        assertNotNull(kf.newLinkRealizationDAO());
        assertNotNull(kf.newLinkTemplateDAO());
        assertNotNull(kf.newMediaDAO());
    }

}
