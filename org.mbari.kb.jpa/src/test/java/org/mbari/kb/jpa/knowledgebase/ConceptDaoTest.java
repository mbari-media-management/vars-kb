package org.mbari.kb.jpa.knowledgebase;


import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration testing. You need to use this against a development database that
 * actually contains data
 */
@TestInstance(Lifecycle.PER_CLASS)
public class ConceptDaoTest {

    public final Logger log = LoggerFactory.getLogger(getClass());
    // private final ConceptDAO conceptDAO;

    // public ConceptDaoTest() {
    // Injector injector = Guice.createInjector(new VarsJpaDevelopmentModule());
    // KnowledgebaseDAOFactory knowledgebaseDAOFactory =
    // injector.getInstance(KnowledgebaseDAOFactory.class);
    // conceptDAO = knowledgebaseDAOFactory.newConceptDAO();
    // }

    // @Test
    // @Disabled
    // public void testFindByName() {
    // String name = "Nanomia";
    // Concept concept = conceptDAO.findByName(name);
    // log.info("conceptDAO.findByName('" + name + "') returns: " + concept);
    // }

    // @Test
    // @Disabled
    // public void testFindRoot() {
    // Concept concept = conceptDAO.findRoot();
    // log.info("conceptDAO.findRoot() returns: " + concept);
    // }

    // @Test
    // @Disabled
    // public void testAll() {
    // Collection<Concept> concept = conceptDAO.findAll();
    // log.info("conceptDAO.findAll() returns " + concept.size() + " concepts");
    // }

    // @Test
    // @Disabled
    // public void testFindDescendentNames() {
    // Collection<ConceptName> names =
    // conceptDAO.findDescendentNames(conceptDAO.findByName("Nanomia"));
    // log.info("conceptDAO.findDescendentNames('Nanomia') returned: " + names);
    // }

    // @AfterAll
    // public void cleanup() {
    // ConceptDAO dao = conceptDAO;
    // dao.startTransaction();
    // Concept concept = dao.findRoot();
    // dao.endTransaction();
    // if (concept != null) {
    // dao.cascadeRemove(concept);
    // }
    // dao.commit();

    // Collection<Concept> badData = dao.findAll();

    // if (badData.size() > 0) {

    // // String s = "Concepts that shouldn't still be in the database:\n";
    // // for (Concept c : badData) {
    // // s += "\n" + entityUtilities.buildTextTree(c) + "\n";
    // // }
    // // log.info(s);
    // log.info("There are " + badData.size()
    // + " concepts that are still in the knowledgebase that shouldn't be there.");
    // }
    // }

}
