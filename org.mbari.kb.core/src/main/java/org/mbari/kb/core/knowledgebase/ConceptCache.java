package org.mbari.kb.core.knowledgebase;

import org.mbari.kb.core.ILink;

import java.util.List;
import java.util.Optional;

/**
 * @author Brian Schlining
 * @since 2016-11-15T10:35:00
 */
public interface ConceptCache {

    List<String> findAllNames();

    List<String> findDescendantNamesFor(Concept concept);

    List<ILink> findLinkTemplatesFor(Concept concept);

    Optional<Concept> findConceptByName(String name);

    Concept findRootConcept();

    void clear();



}
