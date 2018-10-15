package vars.knowledgebase.jpa;

import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vars.jpa.EntityUtilities;
import vars.jpa.VarsJpaTestModule;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.History;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.KnowledgebaseFactory;
import vars.testing.KnowledgebaseTestObjectFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

@TestInstance(Lifecycle.PER_CLASS)
public class HistoryCrudTest {

    public final Logger log = LoggerFactory.getLogger(getClass());
    KnowledgebaseDAOFactory daoFactory;
    EntityUtilities entityUtilities;
    KnowledgebaseFactory kbFactory;
    KnowledgebaseTestObjectFactory testObjectFactory;

    @Test
    public void crudTest01() {

        log.info("---------- TEST: crudTest01 ----------");
        ConceptDAO dao = daoFactory.newConceptDAO();
        Concept concept = testObjectFactory.makeObjectGraph("crudTest01", 4);
        dao.startTransaction();
        dao.persist(concept);
        dao.endTransaction();
        // log.info("INITIAL KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Insert
        History history = testObjectFactory.makeHistory();
        dao.startTransaction();
        concept = dao.merge(concept);
        concept.getConceptMetadata().addHistory(history);
        dao.persist(history);
        dao.endTransaction();
        // log.info("INSERTED " + history + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Update
        dao.startTransaction();
        history = dao.merge(history);
        // history.setComment("WOOOGA WOOGA WOOOGA");
        dao.endTransaction();
        // log.info("UPDATED " + history + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Delete
        dao.startTransaction();
        concept = dao.merge(history.getConceptMetadata().getConcept());
        history = dao.merge(history);
        concept.getConceptMetadata().removeHistory(history);
        dao.remove(history);
        dao.endTransaction();
        // log.info("DELETED " + history + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        dao.cascadeRemove(concept);

    }

    @BeforeAll
    public void setup() {
        Injector injector = Guice.createInjector(new VarsJpaTestModule());
        kbFactory = injector.getInstance(KnowledgebaseFactory.class);
        testObjectFactory = new KnowledgebaseTestObjectFactory(kbFactory);
        daoFactory = injector.getInstance(KnowledgebaseDAOFactory.class);
        entityUtilities = new EntityUtilities();

        ConceptDAO dao = daoFactory.newConceptDAO();
        dao.startTransaction();
        Concept concept = dao.findRoot();
        dao.endTransaction();
        if (concept != null) {
            dao.cascadeRemove(concept);
        }
        dao.close();
    }

    @AfterAll
    public void cleanup() {
        ConceptDAO dao = daoFactory.newConceptDAO();
        dao.startTransaction();
        Concept concept = dao.findRoot();
        dao.endTransaction();
        if (concept != null) {
            dao.cascadeRemove(concept);
        }
        dao.close();

        Collection<Concept> badData = daoFactory.newConceptDAO().findAll();

        if (badData.size() > 0) {

            // String s = "Concepts that shouldn't still be in the database:\n";
            // for (Concept c : badData) {
            // s += "\n" + entityUtilities.buildTextTree(c) + "\n";
            // }
            // log.info(s);
            log.info("There are " + badData.size()
                    + " concepts that are still in the knowledgebase that shouldn't be there.");
        }
    }

}
