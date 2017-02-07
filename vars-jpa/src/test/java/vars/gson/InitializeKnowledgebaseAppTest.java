package vars.gson;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mbari.net.URLUtilities;
import vars.jpa.VarsJpaDevelopmentModule;
import vars.jpa.VarsJpaTestModule;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.KnowledgebaseDAOFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Brian Schlining
 * @since 2016-11-28T09:04:00
 */
public class InitializeKnowledgebaseAppTest {

    //@Ignore
    @Test
    public void test() {
        URL url = getClass().getResource("/kb/kb-dump.json.zip");
        File file = URLUtilities.toFile(url);
        Injector injector = Guice.createInjector(new VarsJpaTestModule());
        KnowledgebaseDAOFactory knowledgebaseDAOFactory = injector.getInstance(KnowledgebaseDAOFactory.class);

        try {
            InitializeKnowledgebaseApp.run(file, knowledgebaseDAOFactory);
        }
        catch (IOException e) {
            Assert.fail(e.getMessage());
        }



        ConceptDAO dao = knowledgebaseDAOFactory.newConceptDAO();
        Concept root = dao.findRoot();
        Assert.assertNotNull(root);

//        dao.startTransaction();
//        Concept c = dao.findByName("Nanomia bijuga");
//        dao.endTransaction();
//        Assert.assertNotNull(c);
//
//        dao.startTransaction();
//        root = dao.find(root);
//        dao.remove(root);
//        dao.endTransaction();
//        dao.close();


    }
}
