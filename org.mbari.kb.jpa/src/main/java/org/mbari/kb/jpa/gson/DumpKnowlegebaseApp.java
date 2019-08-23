package org.mbari.kb.jpa.gson;

import com.google.gson.Gson;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.jpa.knowledgebase.Factories;

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

        Factories factories = Factories.build();
        KnowledgebaseDAOFactory daoFactory = factories.getKnowledgebaseDAOFactory();
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
