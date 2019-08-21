package vars.gson;

import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptDAO;
import vars.knowledgebase.ConceptMetadata;
import vars.knowledgebase.ConceptName;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.LinkRealization;
import vars.knowledgebase.LinkTemplate;
import vars.knowledgebase.Media;
import vars.knowledgebase.jpa.ConceptImpl;
import vars.knowledgebase.jpa.Factories;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
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

        Factories factories = Factories.build();
        KnowledgebaseDAOFactory daoFactory = factories.getKnowledgebaseDAOFactory();
        run(source, daoFactory);

    }

    public static void run(File file, KnowledgebaseDAOFactory daoFactory) throws IOException {
        // -- Check that KB is empty (Don't want to accidently overwrite a production DB!!)
        ConceptDAO dao = daoFactory.newConceptDAO();
        Concept root = dao.findRoot();
        if (root != null) {
            System.out.println("ABORTED! The knowledgebase isn't empty. \n" +
                    "\tYou need to first delete the knowledebase content.");
            System.exit(-1);
        }

        // -- Load JSON from file
        Concept concept = read(file);

        // -- Insert into database
        dao.startTransaction();
        dao.persist(concept);
        dao.endTransaction();
        dao.close();
    }

    public static Concept read(File file) throws IOException {
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

        if (concept != null) {
            fixRelationships(concept);
        }
        return concept;
    }

    private static Concept read(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String content = scanner.hasNext() ? scanner.next() : "";
        return Constants.GSON.fromJson(content, ConceptImpl.class);
    }

    /**
     * GSON does not correctly set 2-way relationships so we have to fix those here.
     * @param concept
     */
    private static void fixRelationships(Concept concept) {

        ((ConceptImpl) concept).setConceptMetadata(concept.getConceptMetadata());

        ConceptMetadata metadata = concept.getConceptMetadata();
        final Collection<Media> medias = new ArrayList<>(metadata.getMedias());
        for (Media m : medias) {
            metadata.removeMedia(m);
            metadata.addMedia(m);
        }

        final Collection<LinkTemplate> linkTemplates = new ArrayList<>(metadata.getLinkTemplates());
        for (LinkTemplate lt: linkTemplates) {
            metadata.removeLinkTemplate(lt);
            metadata.addLinkTemplate(lt);
        }

        final Collection<LinkRealization> linkRealizations = new ArrayList<>(metadata.getLinkRealizations());
        for (LinkRealization lr : linkRealizations) {
            metadata.removeLinkRealization(lr);
            metadata.addLinkRealization(lr);
        }

        final List<Concept> childConcepts = new ArrayList<>(concept.getChildConcepts());
        for (Concept child: childConcepts) {
            concept.removeChildConcept(child);
            concept.addChildConcept(child);
            fixRelationships(child);
        }

        final List<ConceptName> conceptNames = new ArrayList<>(concept.getConceptNames());
        for (ConceptName name : conceptNames) {
            concept.removeConceptName(name);
            concept.addConceptName(name);
        }
    }

}
