package org.mbari.kb.jpa.knowledgebase;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mbari.kb.jpa.gson.AnnotatedFieldExclusionStrategy;
import org.mbari.kb.jpa.gson.ConceptSerializer;
import org.mbari.kb.jpa.gson.UnderscoreFieldExclusionStrategy;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;

/**
 * @author Brian Schlining
 * @since 2016-11-22T10:25:00
 */
public class GsonDemo {

    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setExclusionStrategies(new UnderscoreFieldExclusionStrategy(),
                        new AnnotatedFieldExclusionStrategy())
                .registerTypeAdapter(ConceptImpl.class, new ConceptSerializer())
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        new GraphAdapterBuilder()
//                .addType(ConceptImpl.class)
//                .addType(ConceptMetadataImpl.class)
//                .registerOn(gsonBuilder);
        Gson gson = gsonBuilder.create();


        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        KnowledgebaseDAOFactory daoFactory = factories.getKnowledgebaseDAOFactory();
        KnowledgebaseFactory kbFactory = factories.getKnowledgebaseFactory();

        ConceptDAO dao = daoFactory.newConceptDAO();
        //ConceptImpl root = (ConceptImpl) dao.findRoot();
        ConceptImpl root = (ConceptImpl) dao.findByName("object");

//        KnowledgebaseTestObjectFactory testObjectFactory = new KnowledgebaseTestObjectFactory(kbFactory);
//        Concept root = testObjectFactory.makeObjectGraph("foo", 1);
//
//        root = new ConceptImpl();
//        ((ConceptImpl) root).setId(100L);
//        ConceptName cn = new ConceptNameImpl();
//        cn.setName("Foo");
//        cn.setNameType("Primary");
//
//        root.addConceptName(cn);
//        root.getConceptMetadata();

//        Media media = new MediaImpl();
//        media.setUrl("http://www.mbari.org/foo.png");
//        //root.getConceptMetadata().addMedia(media);
//
//        LinkTemplate lt = new LinkTemplateImpl();
//        lt.setLinkName("foo");
//        lt.setToConcept("bar");
//        lt.setLinkValue("100");
//        root.getConceptMetadata().addLinkTemplate(lt);

        String dump = gson.toJson(root);
        System.out.println(dump);

        ConceptImpl newRoot = gson.fromJson(dump, ConceptImpl.class);
        System.out.println(newRoot);
    }
}
