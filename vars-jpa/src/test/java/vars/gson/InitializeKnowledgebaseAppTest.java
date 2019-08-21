package vars.gson;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mbari.net.URLUtilities;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.jpa.DerbyTestDAOFactory;
import vars.knowledgebase.jpa.Factories;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Brian Schlining
 * @since 2016-11-28T09:04:00
 */
public class InitializeKnowledgebaseAppTest {

    // @Ignore
    @Test
    public void test() {
        URL url = getClass().getResource("/kb/kb-dump.json.zip");
        File file = URLUtilities.toFile(url);
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        KnowledgebaseDAOFactory knowledgebaseDAOFactory = factories.getKnowledgebaseDAOFactory();

        try {
            InitializeKnowledgebaseApp.run(file, knowledgebaseDAOFactory);
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }

        ConceptDAO dao = knowledgebaseDAOFactory.newConceptDAO();
        Concept root = dao.findRoot();
        Assertions.assertNotNull(root);

        // dao.startTransaction();
        // Concept c = dao.findByName("Nanomia bijuga");
        // dao.endTransaction();
        // Assert.assertNotNull(c);
        //
        // dao.startTransaction();
        // root = dao.find(root);
        // dao.remove(root);
        // dao.endTransaction();
        // dao.close();

    }
}
