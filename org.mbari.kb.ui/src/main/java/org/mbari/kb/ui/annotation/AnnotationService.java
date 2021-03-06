package org.mbari.kb.ui.annotation;

import java.util.Collection;

/**
 * @author Brian Schlining
 * @since 2016-11-15T14:44:00
 */
public interface AnnotationService {

    /**
     * This should update both Observations and Associations
     */
    void updateConceptUsedByAnnotations(String newConcept, Collection<String> oldConcepts);


    /**
     *
     * @param concepts
     * @return
     */
    int countByConcepts(Collection<String> concepts);
}
