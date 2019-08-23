package org.mbari.kb.jpa.knowledgebase;

import org.mbari.text.IgnoreCaseToStringComparator;
import org.mbari.kb.core.CacheClearedEvent;
import org.mbari.kb.core.CacheClearedListener;
import org.mbari.kb.core.ILink;
import org.mbari.kb.core.LinkBean;
import org.mbari.kb.core.PersistenceCache;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptCache;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.ConceptName;
import org.mbari.kb.core.knowledgebase.ConceptNameDAO;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.LinkTemplateDAO;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Brian Schlining
 * @since 2016-11-15T09:19:00
 */
public class ConceptCacheImpl implements ConceptCache {

    private final KnowledgebaseDAOFactory factory;
    private final Map<String, Concept> concepts = new ConcurrentHashMap<>();
    private final Map<Concept, List<ILink>> linkTemplates = new ConcurrentHashMap<>();
    private volatile List<String> allNames;
    private final Map<Concept, List<String>> descendantNames = new ConcurrentHashMap<>();
    private final Comparator<Object> comparator = new IgnoreCaseToStringComparator();
    private volatile Concept root;
    private final Object lock = new Object();


    public ConceptCacheImpl(KnowledgebaseDAOFactory factory, PersistenceCache persistenceCache) {
        this.factory = factory;
        persistenceCache.addCacheClearedListener(new CacheClearedListener() {
            @Override
            public void afterClear(CacheClearedEvent evt) {}

            @Override
            public void beforeClear(CacheClearedEvent evt) {
                ConceptCacheImpl.this.clear();
            }
        });
    }

    public List<String> findAllNames() {
        if (allNames == null) {
            synchronized (lock) {
                ConceptNameDAO dao = factory.newConceptNameDAO();
                dao.startTransaction();
                allNames = dao.findAllNamesAsStrings();
                dao.endTransaction();
                dao.close();
            }
        }
        return allNames;

    }

    public List<String> findDescendantNamesFor(Concept concept) {
        return descendantNames.computeIfAbsent(concept, c -> {
            ConceptDAO dao = factory.newConceptDAO();
            dao.startTransaction();

            // -- Cache descendant names
            List<String> descendants = dao.findDescendentNames(c)
                    .stream()
                    .map(ConceptName::getName)
                    .sorted(comparator)
                    .collect(Collectors.toList());

            // -- Cache concept too. (For efficiency)
            concepts.put(c.getPrimaryConceptName().getName(), c);
            dao.endTransaction();
            dao.close();
            return descendants;
        });
    }

    @Override
    public Optional<Concept> findConceptByName(String name) {
        Concept rc = concepts.computeIfAbsent(name, n -> {
            ConceptDAO dao = factory.newConceptDAO();
            dao.startTransaction();
            Concept concept = dao.findByName(n);
            if (concept != null) {
                concepts.put(n, concept);
            }
            dao.endTransaction();
            dao.close();
            return concept;
        });
        return Optional.ofNullable(rc);
    }

    @Override
    public List<ILink> findLinkTemplatesFor(Concept concept) {
        return linkTemplates.computeIfAbsent(concept, c -> {
            LinkTemplateDAO dao = factory.newLinkTemplateDAO();
            dao.startTransaction();
            List<ILink> links = dao.findAllApplicableToConcept(concept)
                    .stream()
                    .map(lt -> new LinkBean(lt.getLinkName(), lt.getToConcept(), lt.getLinkValue()))
                    .sorted(comparator)
                    .collect(Collectors.toList());
            dao.endTransaction();
            dao.close();
            return links;
        });
    }

    public Concept findRootConcept() {
        if (root == null) {
            synchronized (lock) {
                ConceptDAO dao = factory.newConceptDAO();
                dao.startTransaction();
                root = dao.findRoot();
                dao.endTransaction();
                dao.close();
            }
        }
        return root;
    }

    public synchronized void clear() {
        descendantNames.clear();
        concepts.clear();
        linkTemplates.clear();
        allNames.clear();
        allNames = null;
        root = null;
    }
}
