package vars.knowledgebase.jpa;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vars.testing.KnowledgebaseTestObjectFactory;
import vars.knowledgebase.KnowledgebaseFactory;
import vars.knowledgebase.Concept;

/**
 * Created by IntelliJ IDEA. User: brian Date: Aug 12, 2009 Time: 11:43:40 AM To
 * change this template use File | Settings | File Templates.
 */
public class KBPojoTest {

    public final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void bigTest() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        KnowledgebaseFactory af = factories.getKnowledgebaseFactory();
        KnowledgebaseTestObjectFactory factory = new KnowledgebaseTestObjectFactory(af);
        Concept c = factory.makeObjectGraph("BIG-TEST", 3);

        // EntityUtilities eu = new EntityUtilities();
        // log.info("KNOWLEDGEBASE TREE FOR toString TEST:\n" + eu.buildTextTree(c));

    }

}
