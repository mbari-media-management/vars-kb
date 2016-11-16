package vars.knowledgebase.ui.annotation;

import java.util.Collection;

/**
 * @author Brian Schlining
 * @since 2016-11-15T14:44:00
 */
public interface AnnotationService {

    // TODO this should update both Observations and Associations
    void updateConceptUsedByAnnotations(String newConcept, Collection<String> oldConcepts);

    void deleteAnnotationsByConcept(String concept);

    int countByConcepts(Collection<String> concepts);
}
