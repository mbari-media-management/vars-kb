package vars.gson;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import vars.jpa.InjectorModule;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.jpa.ConceptImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Load a JSON dump into the database. BUt
 * @author Brian Schlining
 * @since 2016-11-22T14:55:00
 */
public class InitializeKnowledgebaseApp {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            String msg = "Usage: \n java " + InitializeKnowledgebaseApp.class.getName() + " <source file>";
            System.out.println(msg);
        }

        // -- Parse Args
        File source = new File(args[0]);

        // -- Check that KB is empty (Don't want to accidently overwrite a production DB!!)
        Injector injector = Guice.createInjector(new InjectorModule());
        KnowledgebaseDAOFactory daoFactory = injector.getInstance(KnowledgebaseDAOFactory.class);
        ConceptDAO dao = daoFactory.newConceptDAO();
        Concept root = dao.findRoot();
        if (root != null) {
            System.out.println("ABORTED! The knowledgebase isn't empty. \n" +
                "\tYou need to first delete the knowledebase content.");
            System.exit(-1);
        }

        // -- Load JSON from file
        Concept concept = read(source);

        // -- Insert into database
        dao.startTransaction();
        dao.persist(concept);
        dao.endTransaction();
        dao.close();

    }

    private static Concept read(File file) throws IOException {
        Concept concept = null;
        if (file.getName().endsWith(".zip")) {
            ZipFile zipFile = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                try {
                    InputStream stream = zipFile.getInputStream(entry);
                    concept = read(stream);
                    stream.close();
                    if (concept != null) {
                        break;
                    }
                }
                catch (Exception e) {
                    // do nothing
                }
            }
        }
        else {
            InputStream stream = new BufferedInputStream(new FileInputStream(file));
            concept = read(stream);
            stream.close();
        }
        return concept;
    }

    private static Concept read(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String content = scanner.hasNext() ? scanner.next() : "";
        return Constants.GSON.fromJson(content, ConceptImpl.class);
    }
}
