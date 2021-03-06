package org.mbari.kb.jpa.knowledgebase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.core.testing.KnowledgebaseTestObjectFactory;
import org.mbari.kb.jpa.EntityUtilities;


import java.util.Collection;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.ConceptName;
import org.mbari.kb.core.knowledgebase.ConceptNameTypes;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;

/**
 * Created by IntelliJ IDEA. User: brian Date: Oct 30, 2009 Time: 2:02:12 PM To
 * change this template use File | Settings | File Templates.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class ConceptNameCrudTest {

    public final Logger log = LoggerFactory.getLogger(getClass());
    KnowledgebaseDAOFactory daoFactory;

    KnowledgebaseFactory kbFactory;
    KnowledgebaseTestObjectFactory testObjectFactory;
    EntityUtilities entityUtilities;

    @BeforeAll
    public void setup() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());

        kbFactory = factories.getKnowledgebaseFactory();
        testObjectFactory = new KnowledgebaseTestObjectFactory(factories.getKnowledgebaseFactory());
        daoFactory = factories.getKnowledgebaseDAOFactory();
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

    @Test
    public void conceptNameCRUD() {

        log.info("---------- TEST: conceptNameCRUD ----------");
        ConceptDAO dao = daoFactory.newConceptDAO();
        // ConceptNameDAO conceptNameDAO = daoFactory.newConceptNameDAO();
        Concept concept = testObjectFactory.makeObjectGraph("conceptNameCRUD", 2);
        dao.startTransaction();
        dao.persist(concept);
        dao.endTransaction();
        // log.info("INITIAL KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        String name1 = "FOO";
        String name2 = "BAR";

        insert: {

            // Using correct JPA form
            dao.startTransaction();
            concept = dao.findRoot();
            ConceptName conceptName = kbFactory.newConceptName();
            conceptName.setName(name1);
            conceptName.setNameType(ConceptNameTypes.SYNONYM.toString());
            concept.addConceptName(conceptName);
            dao.persist(conceptName);
            dao.endTransaction();

            dao.startTransaction();
            concept = dao.findRoot();
            conceptName = dao.merge(conceptName);
            // log.info("INSERTED " + conceptName + " in KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(concept));
            concept.removeConceptName(conceptName);
            dao.remove(conceptName);
            // log.info("DELETED " + conceptName + " in KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(concept));
            dao.endTransaction();

            // Using old reference to conceptName
            dao.startTransaction();
            concept = dao.merge(concept);
            conceptName = kbFactory.newConceptName();
            conceptName.setName(name1);
            conceptName.setNameType(ConceptNameTypes.SYNONYM.toString());
            log.info("Concept = " + concept);
            log.info("ConceptName = " + conceptName);
            concept.addConceptName(conceptName);
            dao.persist(conceptName);
            dao.endTransaction();
            // log.info("INSERTED " + conceptName + " in KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(concept));

            dao.startTransaction();
            dao.merge(concept);
            concept.removeConceptName(conceptName);
            dao.remove(conceptName);
            dao.endTransaction();
            // log.info("DELETED " + conceptName + " in KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(concept));

            // Insert by inserting a new concept
            dao.startTransaction();
            Concept childConcept = kbFactory.newConcept();
            conceptName = kbFactory.newConceptName();
            conceptName.setName(name1);
            conceptName.setNameType(ConceptNameTypes.PRIMARY.getName());
            childConcept.addConceptName(conceptName);
            concept.addChildConcept(childConcept);
            dao.persist(childConcept);
            dao.endTransaction();
            // log.info("UPDATED " + concept + " in KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(concept));

        }

        update: {
            dao.startTransaction();
            concept = dao.findByName(name1);
            ConceptName conceptName = concept.getConceptName(name1);
            conceptName.setName(name2);
            dao.merge(conceptName);
            dao.endTransaction();

            dao.startTransaction();
            Concept root = dao.findRoot();
            concept = dao.findByName(concept.getPrimaryConceptName().getName());
            // concept = dao.merge(concept);
            // log.info("UPDATED KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(root));
            conceptName = kbFactory.newConceptName();
            conceptName.setName(name1);
            conceptName.setNameType(ConceptNameTypes.SYNONYM.toString());
            concept.addConceptName(conceptName);
            dao.persist(conceptName);
            dao.endTransaction();

            dao.startTransaction();
            root = dao.findRoot();
            dao.endTransaction();
            // log.info("UPDATED KNOWLEDGEBASE TREE:\n" +
            // entityUtilities.buildTextTree(root));
        }

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
