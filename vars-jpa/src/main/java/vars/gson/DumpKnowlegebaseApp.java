package vars.gson;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import vars.jpa.InjectorModule;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.KnowledgebaseDAOFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * @author Brian Schlining
 * @since 2016-11-22T14:34:00
 */
public class DumpKnowlegebaseApp {

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            String msg = "Usage: \n java " + DumpKnowlegebaseApp.class.getName() + " <target file>";
            System.out.println(msg);
        }

        // -- Parse Args
        File target = new File(args[0]);

        // -- Load root Concept from KB
        Injector injector = Guice.createInjector(new InjectorModule());
        KnowledgebaseDAOFactory daoFactory = injector.getInstance(KnowledgebaseDAOFactory.class);
        ConceptDAO dao = daoFactory.newConceptDAO();
        Concept root = dao.findRoot();

        // -- Convert to JSON
        Gson gson = Constants.GSON;
        String json = gson.toJson(root);

        // -- Write to file
        try(Writer out = new BufferedWriter(new FileWriter(target))) {
            out.write(json);
        }

    }
}
