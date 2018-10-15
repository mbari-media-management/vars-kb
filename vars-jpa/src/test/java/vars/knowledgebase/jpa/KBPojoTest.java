package vars.knowledgebase.jpa;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.Guice;
import vars.testing.KnowledgebaseTestObjectFactory;
import vars.knowledgebase.KnowledgebaseFactory;
import vars.knowledgebase.Concept;
import vars.jpa.VarsJpaTestModule;

/**
 * Created by IntelliJ IDEA. User: brian Date: Aug 12, 2009 Time: 11:43:40 AM To
 * change this template use File | Settings | File Templates.
 */
public class KBPojoTest {

    public final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void bigTest() {
        Injector injector = Guice.createInjector(new VarsJpaTestModule());
        KnowledgebaseFactory af = injector.getInstance(KnowledgebaseFactory.class);
        KnowledgebaseTestObjectFactory factory = new KnowledgebaseTestObjectFactory(af);
        Concept c = factory.makeObjectGraph("BIG-TEST", 3);

        // EntityUtilities eu = new EntityUtilities();
        // log.info("KNOWLEDGEBASE TREE FOR toString TEST:\n" + eu.buildTextTree(c));

    }

}
